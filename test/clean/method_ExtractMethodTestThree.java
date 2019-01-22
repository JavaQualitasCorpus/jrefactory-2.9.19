package method;

import java.io.*;

public class ExtractMethodTestThree {
	public ExtractMethodTestThree() {
	}

	public void transcribe (InputStream input, OutputStream output) throws IOException {
		int bufferSize = 1024 * 8;
		byte[] buffer = new byte[bufferSize];
		int bytesRead = input.read(buffer);
		while (bytesRead >= 0) {
			output.write(buffer, 0, bytesRead);
			bytesRead = input.read(buffer);
		}
	}
}

