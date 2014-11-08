package org.firepick.firesight;

public abstract class NativeObject {
	static {
		FireSight.loadLibraries();
	}

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
