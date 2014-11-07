package org.firepick.firesight;

public abstract class NativeObject {

	protected long nativeObject;
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (nativeObject != 0) {
			dispose();
		}
	}

	public abstract void dispose();

}
