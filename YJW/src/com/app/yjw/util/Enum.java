package com.app.yjw.util;

public class Enum {

	public enum UpdateStyle {
		ForceUpdate, NoUpdate, OptionalUpdate, UpdateError;
		public static int Cast(UpdateStyle style) {
			int rs = 0;
			switch (style) {
			case NoUpdate:
				rs = 0;
				break;
			case OptionalUpdate:
				rs = 1;
				break;
			case ForceUpdate:
				rs = 2;
				break;
			case UpdateError:
				rs = 4;
				break;
			}
			return rs;
		};

		public static UpdateStyle Cast(int x) {
			UpdateStyle style = NoUpdate;
			switch (x) {
			case 0:
				break;
			case 1:
				style = OptionalUpdate;
				break;
			case 2:
				style = ForceUpdate;
				break;
			case 3:
				style = UpdateError;
				break;
			}
			return style;
		}
	}

}
