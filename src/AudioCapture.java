/*File AudioCapture0.java
This program demonstrates the capture and 
subsequent playback of audio data.

A GUI appears on the screen containing the 
following buttons:
Capture
Stop
Playback

Input data from a microphone is captured and 
saved in a ByteArrayOutputStream object when the
user clicks the Capture button.

Data capture stops when the user clicks the Stop 
button.

Playback begins when the user clicks the Playback
button.

This version of the program gets and  displays a
list of available mixers, producing the following
output:

Available mixers:
Java Sound Audio Engine
Built-in Input

Then the program gets and uses one of the 
available mixers.

The Java Sound Audio Engine will not work in
this program. It fails due to a 
data format compatibility problem.

 ************************************************/

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

public class AudioCapture {

	boolean stopCapture = false;
	ByteArrayOutputStream byteArrayOutputStream;
	AudioFormat audioFormat;
	TargetDataLine targetDataLine;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;
	private String wavName;
	private File audioFile;

	// This method captures audio input from a
	// microphone and saves it in a
	// ByteArrayOutputStream object.
	public void captureAudio() {

		// request name of file to be captured
		wavName = JOptionPane.showInputDialog(null,
				"please enter name of file to be recorded:");
		System.out.println(wavName);

		try {
			// Get and display a list of
			// available mixers.
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
			}// end for loop

			// Get everything set up for capture
			audioFormat = getAudioFormat();

			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);

			// Select one of the available
			// mixers.
			Mixer mixer = AudioSystem.getMixer(mixerInfo[1]);

			// Get a TargetDataLine on the selected
			// mixer.
			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
			// Prepare the line for use.
			targetDataLine.open(audioFormat);
			targetDataLine.start();

			// Create a thread to capture the microphone
			// data and start it running. It will run
			// until the Stop button is clicked.
			Thread captureThread = new CaptureThread();
			captureThread.start();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}// end catch
	}// end captureAudio method

	// This method plays back the audio data that
	// has been saved in the ByteArrayOutputStream
	public void playAudio() {

		try {
			// File soundFile = new File(textField.getText());
			audioInputStream = AudioSystem.getAudioInputStream(audioFile);
			audioFormat = audioInputStream.getFormat();
			System.out.println(audioFile.getName() + " " + audioFormat);

			DataLine.Info dataLineInfo = new DataLine.Info(
					SourceDataLine.class, audioFormat);

			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			// Create a thread to play back the data and
			// start it running. It will run until the
			// end of file, or the Stop button is
			// clicked, whichever occurs first.
			// Because of the data buffers involved,
			// there will normally be a delay between
			// the click on the Stop button and the
			// actual termination of playback.
			new PlayThread().start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}// end catch

		// try {
		// // Get everything set up for playback.
		// // Get the previously-saved data into a byte
		// // array object.
		// byte audioData[] = byteArrayOutputStream.toByteArray();
		// // Get an input stream on the byte array
		// // containing the data
		// InputStream byteArrayInputStream = new ByteArrayInputStream(
		// audioData);
		// AudioFormat audioFormat = getAudioFormat();
		// audioInputStream = new AudioInputStream(byteArrayInputStream,
		// audioFormat, audioData.length / audioFormat.getFrameSize());
		// DataLine.Info dataLineInfo = new DataLine.Info(
		// SourceDataLine.class, audioFormat);
		// sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		// sourceDataLine.open(audioFormat);
		// sourceDataLine.start();
		//
		// // Create a thread to play back the data and
		// // start it running. It will run until
		// // all the data has been played back.
		// Thread playThread = new PlayThread();
		// playThread.start();
		// } catch (Exception e) {
		// System.out.println(e);
		// System.exit(0);
		// }// end catch
	}// end playAudio

	// This method creates and returns an
	// AudioFormat object for a given set of format
	// parameters. If these parameters don't work
	// well for you, try some of the other
	// allowable parameter values, which are shown
	// in comments following the declarations.
	private AudioFormat getAudioFormat() {
		float sampleRate = 44100.0F;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}// end getAudioFormat
		// =============================================//

	// Inner class to capture data from microphone
	class CaptureThread extends Thread {
		// An arbitrary-size temporary holding buffer
		byte tempBuffer[] = new byte[10000];

		public void run() {
			// record as wave
			AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
			// takes user input file name and appends filetype
			audioFile = new File(wavName + ".wav");

			try {
				targetDataLine.open(audioFormat);
				targetDataLine.start();
				AudioSystem.write(new AudioInputStream(targetDataLine),
						fileType, audioFile);
			} catch (Exception e) {
				e.printStackTrace();
			}// end catch

			// byteArrayOutputStream = new ByteArrayOutputStream();
			// stopCapture = false;
			// try {// Loop until stopCapture is set by
			// // another thread that services the Stop
			// // button.
			// while (!stopCapture) {
			// // Read data from the internal buffer of
			// // the data line.
			// int cnt = targetDataLine.read(tempBuffer, 0,
			// tempBuffer.length);
			// if (cnt > 0) {
			// // Save data in output stream object.
			// byteArrayOutputStream.write(tempBuffer, 0, cnt);
			// }// end if
			// }// end while
			// byteArrayOutputStream.close();
			// } catch (Exception e) {
			// System.out.println(e);
			// System.exit(0);
			// }// end catch
		}// end run
	}// end inner class CaptureThread
		// ===================================//

	// Inner class to play back the data
	// that was saved.

	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {

			try {
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();

				int cnt;
				// Keep looping until the input read method
				// returns -1 for empty stream or the
				// user clicks the Stop button causing
				// stopPlayback to switch from false to
				// true.
				while ((cnt = audioInputStream.read(tempBuffer, 0,
						tempBuffer.length)) != -1) {
					if (cnt > 0) {
						// Write data to the internal buffer of
						// the data line where it will be
						// delivered to the speaker.
						sourceDataLine.write(tempBuffer, 0, cnt);
					}// end if
				}// end while
					// Block and wait for internal buffer of the
					// data line to empty.
				sourceDataLine.drain();
				sourceDataLine.close();

				// Prepare to playback another file
				// stopBtn.setEnabled(false);
				// playBtn.setEnabled(true);
				// stopPlayback = false;
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}// end catch

			// try {
			// int cnt;
			//
			// // Keep looping until the input read method
			// // returns -1 for empty stream.
			// while ((cnt = audioInputStream.read(tempBuffer, 0,
			// tempBuffer.length)) != -1) {
			// if (cnt > 0) {
			// // Write data to the internal buffer of
			// // the data line where it will be
			// // delivered to the speaker.
			// sourceDataLine.write(tempBuffer, 0, cnt);
			// }// end if
			// }// end while
			// // Block and wait for internal buffer of the
			// // data line to empty.
			// sourceDataLine.drain();
			// sourceDataLine.close();
			// } catch (Exception e) {
			// System.out.println(e);
			// System.exit(0);
			// }// end catch
		}// end run
	}// end inner class PlayThread
		// =============================================//

}// end outer class AudioCapture.java
