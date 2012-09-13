/**
 * contains the wrapper for the header
 * Initialises a WaveHeader object with the default values
 */
public class WaveHeader {

	public String sGroupID; // RIFF
	public int dwFileLength; // total file length minus 8, which is taken up by
								// RIFF
	public String sRiffType; // always WAVE

	public WaveHeader() {

		dwFileLength = 0;
		sGroupID = "RIFF";
		sRiffType = "WAVE";
	}
}
