/**
 * Initialises a new data chunk with default values.
 * 
 * @author robertwilson
 *
 */
public class WaveDataChunk {

	public String sChunkID; // "data"
	public int dwChunkSize; // Length of header in bytes
	public int[] shortArray; // 8-bit audio

	public WaveDataChunk() {

		shortArray = new int[0];
		dwChunkSize = 0;
		sChunkID = "data";
	}
}
