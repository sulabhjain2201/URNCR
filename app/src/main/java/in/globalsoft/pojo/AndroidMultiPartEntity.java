package in.globalsoft.pojo;


import org.apache.http.entity.mime.MultipartEntity;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AndroidMultiPartEntity extends MultipartEntity {

	private final ProgressListener listener;

	public AndroidMultiPartEntity(ProgressListener progressListener) {
		super();
		//super(progressListener);
		listener=progressListener;
	}


	@Override
	public void writeTo(final OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream, this.listener));
	}

	public  interface ProgressListener {
		void transferred(long num);
	}

	public static class CountingOutputStream extends FilterOutputStream {

		private final ProgressListener listener;
		private long transferred;

		public CountingOutputStream(final OutputStream out,
				final ProgressListener listener) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
		}

		public void write(byte[] b, int off, int len) throws IOException {
			out.write(b, off, len);
			this.transferred += len;
			this.listener.transferred(this.transferred);
		}

		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.transferred(this.transferred);
		}
	}
}
