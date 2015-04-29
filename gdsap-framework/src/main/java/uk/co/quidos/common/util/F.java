package uk.co.quidos.common.util;


public class F extends F_Father {
	
	// 选择第一个数
	public static double selectFirst(double...vals){
		for (double d : vals) {
			if(d>0) return d;
		}
		return 0;
	}

	/**
	 * 计算一个数组的和
	 * @param data
	 * @return
	 */
	public static double sum(double[] data) {
		double r = 0;
		for (double d : data) {
			r += d;
		}
		return F.f4(r);
	}

	/**
	 * 求一列的值的和
	 * @param data
	 * @param cn
	 * @return
	 */
	public static double sum(double[][] data, int cn) {
		double d = -100;
		for (int i = 0; i < data.length; i++) {
			d += data[i][cn];
		}
		return F.f4(d);
	}
	public static int sum(int[][] data, int cn) {
		int d = 0;
		for (int i = 0; i < data.length; i++) {
			d = d + data[i][cn-1];
		}
		return d;
	}

	

	/**
	 * 一个数 减去一个数组
	 * 
	 * @param data
	 * @param by
	 * @return
	 */
	public static double[] sub(double data, double by[]) {
		double[] r = new double[by.length];
		for (int i = 0; i < by.length; i++) {
			r[i] = data - by[i];
			r[i] = F.f4(r[i]);
		}
		return r;
	}

	/**
	 * 数组1 减去 数组2
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double[] sub(double[] data, double[] by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = data[i] - by[i];
			r[i] = F.f4(r[i]);
		}
		return r;
	}

	public static double[] sub(int[] data, int[] by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = data[i] - by[i];
		}
		return r;
	}

	/**
	 * 数组 减去一个数
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double[] sub(double[] data1, double by) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			r[i] = data1[i] - by;
			r[i] = F.f4(r[i]);
		}
		return r;
	}

	/**
	 * 数组的反余弦
	 * 
	 * @param data
	 * @return
	 */
	public static double[] cos(double data[]) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = Math.cos(data[i]);
			r[i] = F.f4(r[i]);
		}

		return r;
	}

	// 两个数组相乘(简单的数组相乘)
	public static double[] mul(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			r[i] = data1[i] * data2[i];
			r[i] = F.f4(r[i]);
		}
		return r;
	}

	/**
	 * 数组中每个值乘以一个值
	 * @param data
	 * @param by
	 * @return
	 */
	public static double[] mul(double[] data, double by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = F.f4(data[i] * by);
		}
		return r;
	}

	/**
	 * 一个数除以一个数组
	 * @param data
	 * @param by
	 * @return
	 */
	public static double[] div(double data, double[] by) {
		double[] r = new double[by.length];
		for (int i = 0; i < by.length; i++) {
			if (by[i] == 0) {
				r[i] = 0;
			} else {
				r[i] = F.f4(data / by[i]);
			}

		}
		return r;
	}

	/**
	 * 数组除以一个数
	 * @param data
	 * @param by
	 * @return
	 */
	public static double[] div(double[] data, double by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			if (by == 0) {
				r[i] = 0;
			} else {
				r[i] = F.f4(data[i] / by);
			}

		}
		return r;
	}

	/**
	 * 一个数组除以一个数组
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double[] div(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			if (data2[i] == 0) {
				r[i] = 0;
			} else {
				r[i] = F.f4(data1[i] / data2[i]);
			}
		}
		return r;
	}

	/**
	 * 求数组中最大值
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double[] Max(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < r.length; i++) {
			r[i] = data1[i] > data2[i] ? data1[i] : data2[i];
		}
		return r;
	}

	// 复制数组
	public static int[][] copy(int[][] data) {
		int[][] r = new int[data.length][data[0].length];
		for (int a = 0; a < data.length; a++) {
			for (int b = 0; b < data[a].length; b++) {
				r[a][b] = data[a][b];
			}
		}
		return r;
	}

	// 把一个数 转换成 12 个数
	public static double[] copy(double data) {
		double[] r = new double[12];
		for (int i = 0; i < r.length; i++) {
			r[i] = F.f4(data);
		}
		return r;
	}

	/**
	 * 把夏天的值 复位0 (6 - 9)为夏天
	 * 
	 * @return
	 */
	public static double[] summer(double[] data) {
		double[] r = F.copy(data);
		r[5] = 0;
		r[6] = 0;
		r[7] = 0;
		r[8] = 0;
		return r;
	}

	public static double[] setSummer(double[] data, double val) {
		double[] r = F.copy(data);
		r[5] = val;
		r[6] = val;
		r[7] = val;
		r[8] = val;
		return r;
	}

	public static boolean include(int val, Integer... vals) {
		for (int i : vals) {
			if (val == i) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean include(float val, Float... vals) {
		for (float i : vals) {
			if (val == i) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 是否 包含  zhanghuihua
	 */
	public static boolean in(String val, String... vals) {
		if (val == null) return false;
		for (String s : vals) {
			if (val.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 内插法
	 */
	public static double interpolation(double X, double x1, double y1, double x2, double y2) {
		double r = y1 + (y2 - y1) / (x2 - x1) * (X - x1);
		return r;
	}

	/**
	 * 
	 * @param C1
	 *            参照列
	 * @param C2
	 *            计算列
	 * @param data计算数组
	 * @param X1
	 * @param Y1
	 * @param X2
	 * @param Y2
	 * @return
	 */
	public static double autoInterpolation(int C1, int C2, double X, double[][] data) {
		// 如果只有一列值 直接返回
		if (data.length == 1) {
			return data[0][C2];

		}
		// 如果有相等的直接返回相等的
		double[] CI = getC(data, C1);
		if (eq(X, CI) != -1) {
			return data[eq(X, CI)][C2];
			// 进行内插法
		} else {
			int[] c = close(X, CI);
			return F.f4(interpolation(X, data[c[0]][C1], data[c[0]][C2], data[c[1]][C1], data[c[1]][C2]));
		}
	}

	public static double autoInterpolation1(int C1, int C2, double X, double[][] data) {
		// 如果只有一列值 直接返回
		if (data.length == 1) {
			return data[0][C2];

		}
		// 如果有相等的直接返回相等的
		double[] CI = getC(data, C1);
		if (eq(X, CI) != -1) {
			return data[eq(X, CI)][C2];
			// 进行内插法
		} else {
			int[] c = close(X, CI);
			return F.f4(1/interpolation(X, data[c[0]][C1], 1/data[c[0]][C2], data[c[1]][C1], 1/data[c[1]][C2]));
		}
	}

}
