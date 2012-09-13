/**
 * Initialises a format chunk with the following properties:
 * Sample rate: 44100 Hz
 * Channels: Stereo
 * Bit depth: 16-bit
 */

public class WaveFormatChunk {

	public String sChunkID; // Four bytes: "fmt "
	public int dwChunkSize; // Length of header in bytes
	public int wFormatTag; // 1 (MS PCM)
	public int wChannels; // Number of channels
	public int dwSamplesPerSec; // Frequency of the audio in Hz... 44100
	public int dwAvgBytesPerSec; // for estimating RAM allocation
	public int wBlockAlign; // sample frame size, in bytes
	public int wBitsPerSample; // bits per sample

	public WaveFormatChunk() {

		sChunkID = "fmt ";
		dwChunkSize = 16;
		wFormatTag = 1;
		wChannels = 2;
		dwSamplesPerSec = 44100;
		wBitsPerSample = 16;
		wBlockAlign = wChannels * (wBitsPerSample / 8);
		dwAvgBytesPerSec = dwSamplesPerSec * wBlockAlign;
	}
}
