package uk.co.quidos.common.util;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class F_Father {
	private final static MathContext mathContext=MathContext.DECIMAL64;
	
	/**
	 * 0.175+0.15 = 0.32499999999999996 类似这种java误差问题0.00000001后，再做四舍五入
	 * @param n
	 * @param scale
	 * @return 
	 */
	private static double _f(double n, int scale) {
		return BigDecimal.valueOf(n).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 保留四位
	 * @param n
	 * @return
	 */
	public static double f4(double n) {
		return _f(n, 4);
	}
	
	/**
	 * 保留三位
	 * @param n
	 * @return
	 */
	public static double f3(double n) {
		// return Math.round(n*1000.0)/1000.0;
		return _f(n, 3);
	}

	/**
	 * 保留两位
	 * @param n
	 * @return
	 */
	public static double f2(double n) {
		// return Math.round(n*100.0)/100.0;
		return _f(n, 2);
	}

	/**
	 * 保留一位
	 * @param n
	 * @return
	 */
	public static double f1(double n) {
		// return Math.round(n*10.0)/10.0;
		return _f(n, 1);
	}

	/**
	 * 保留整数
	 * @param n
	 * @return
	 */
	public static int f(double n) {
		return BigDecimal.valueOf(n).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	/**
	 * 保留整数
	 * @param numbers
	 * @return
	 */
	public static int[] f(double[] numbers) {
		int [] data = new int[numbers.length];
		for (int i = 0; i < data.length; i++) {
			data[i] = f(numbers[i]);
		}
		return data;
	}

	public static double[] fSAP(double sap[]) {
		double SAP[] = new double[6];
		for (int b = 0; b < 6; b++) {
			int k = b;
			if (k == 5) {
				k++;
			}
			SAP[b] = sap[k];
		}
		return SAP;
	}

	public static int[][] toInt(double data[][]) {
		if (data.length == 0) {
			return null;
		}
		int r[][] = new int[data.length][data[0].length];
		for (int a = 0; a < data.length; a++) {
			for (int b = 0; b < data[0].length; b++) {
				r[a][b] = (int) data[a][b];
			}
		}
		return r;
	}

	public static double[] toDouble(int data[]) {
		if (data.length == 0) {
			return null;
		}
		double r[] = new double[data.length];
		for (int a = 0; a < data.length; a++) {
			r[a] = (double) data[a];
		}
		return r;
	}

	public static double max(double data[]) {
		double a = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] > a) {
				a = data[i];
			}
		}
		return a;
	}

	public static double min(double data[]) {
		double a = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] < a) {
				a = data[i];
			}
		}
		return a;
	}

	public static double[] getC(double[][] data, int no) {
		double[] a = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			a[i] = data[i][no];
		}
		return a;
	}

	public static double[][] unite(double A[], double B[]) {
		int l = Math.max(A.length, B.length);
		double C[][] = new double[2][l];
		for (int i = 0; i < A.length; i++) {
			C[0][i] = A[i];
		}
		for (int i = 0; i < A.length; i++) {
			C[1][i] = B[i];
		}
		return C;
	}

	public static double[][] unite(double A[][], double B[][]) {
		int l = 0;
		if (A.length > 0 && B.length > 0) {
			l = Math.max(A[0].length, B[0].length);
		} else if (A.length > 0) {
			l = A[0].length;
		} else if (B.length > 0) {
			l = B[0].length;
		}

		double[][] C = new double[A.length + B.length][l];
		if (A.length > 0) {
			for (int a = 0; a < A.length; a++) {
				for (int b = 0; b < A[0].length; b++) {
					C[a][b] = A[a][b];
				}
			}
		}
		if (B.length > 0) {
			for (int a = 0; a < B.length; a++) {
				for (int b = 0; b < B[0].length; b++) {
					C[a + A.length][b] = B[a][b];
				}
			}
		}
		return C;
	}

	public static double[][] unite(double A[][], double B[]) {
		int l = Math.max(A[0].length, B.length);
		double[][] C = new double[A.length + 1][l];
		for (int a = 0; a < A.length; a++) {
			for (int b = 0; b < A[0].length; b++) {
				C[a][b] = A[a][b];
			}
		}

		for (int b = 0; b < B.length; b++) {
			C[A.length][b] = B[b];
		}

		return C;
	}

	/**
	 * 求和
	 * @param vals
	 * @return
	 */
	public static double sumAll(double... vals) {
		return sum(vals);
	}

	/**
	 * 计算一个数组的和
	 */
	public static double sum(double[] data) {
		BigDecimal r =new BigDecimal(0);
		for (double d : data) {
			r = r.add(BigDecimal.valueOf(d),mathContext);
		}
		return r.doubleValue();
	}

	/**
	 * 求一列的值的和
	 * @param data
	 * @param cn
	 * @return
	 */
	public static double sum(double[][] data, int cn) {
		BigDecimal d = new BigDecimal(0);
		if(data[0].length <cn)
				return -100;
		for (int i = 0; i < data.length; i++) {
			d=d.add(BigDecimal.valueOf(data[i][cn]));
		}
		return d.doubleValue();
	}

	
	/**
	 *  相同长度数组相加
	 * @param datas
	 * @return
	 */
	public static double[] sum(double[]... datas) {
		if(datas == null)
			return null;
		double[] r = new double[datas[0].length];
		for (int i = 0; i < r.length; i++) {
			for(double[] data : datas){
				r[i] = sumAll(r[i],data[i]);
			}
		}
		return r;
	}

	/**
	 * 数组加上一个值
	 * @param data
	 * @param by
	 * @return
	 */
	public static double[] sumBy(double[] data, double by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = sumAll(data[i],by);
		}
		return r;
	}
	
	/**
	 * 所有数相减
	 * @param vals
	 * @return
	 */
	public static double subAll(double... vals){
		BigDecimal r=BigDecimal.valueOf(vals[0]);
		for(int i=1;i<vals.length;i++){
			r =r.subtract(BigDecimal.valueOf(vals[i]),mathContext);
		}
		return r.doubleValue();
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
			r[i] = subAll(data , by[i]);
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
			r[i] = subAll(data[i],by[i]);
		}
		return r;
	}

	public static double[] sub(int[] data, int[] by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = subAll(data[i],by[i]);
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
			r[i] = subAll(data1[i],by);
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
		}

		return r;
	}
	
	
	/**
	 * 多个数相乘
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double mulAll(double... vals) {
		BigDecimal r=BigDecimal.valueOf(vals[0]);
		for(int i=1;i<vals.length;i++){
			r = r.multiply(BigDecimal.valueOf(vals[i]),mathContext);
		}
		return r.doubleValue();
	}
	
	/**
	 * 两数相乘
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double mul(double data1, double data2) {
		return mulAll(data1,data2);
	}
	
	/**
	 *  两个数组相乘(简单的数组相乘)
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static double[] mul(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			r[i] = mul(data1[i], data2[i]);
			r[i] = r[i];
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
			r[i] = mul(data[i], by);
		}
		return r;
	}
	
	/**
	 * 多数相除
	 * @param vals
	 * @return
	 */
	public static double divAll(double... vals) {
		BigDecimal r=BigDecimal.valueOf(vals[0]);
		for(int i=1;i<vals.length;i++){
			if(vals[i] == 0)
				continue;
			r = r.divide(BigDecimal.valueOf(vals[i]),mathContext);
		}
		return r.doubleValue();
	}
	
	//两数相除
	/**
	 * data分子
	 * by分母
	 */
	public static double div(double data, double by) {
		if(by ==0)
			return 0;
		return divAll(data,by);
	}

	// 一个数除以一个数组
	public static double[] div(double data, double[] by) {
		double[] r = new double[by.length];
		for (int i = 0; i < by.length; i++) {
			if (by[i] == 0) {
				r[i] = 0;
			} else {
				r[i] = div(data,by[i]);
			}

		}
		return r;
	}

	// 数组除以一个数
	public static double[] div(double[] data, double by) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			if (by == 0) {
				r[i] = 0;
			} else {
				r[i] = div(data[i],by);
			}

		}
		return r;
	}

	// 一个数组除以一个数组
	public static double[] div(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			if (data2[i] == 0) {
				r[i] = 0;
			} else {
				r[i] = div(data1[i],data2[i]);
			}
		}
		return r;
	}
	// 一个数组除以一个数组 ,
	public static double[] div(double[] data1, double[] data2,int round) {
		double[] r = new double[data1.length];
		for (int i = 0; i < data1.length; i++) {
			if (data2[i] == 0) {
				r[i] = 0;
			} else {
				r[i] = _f(div(data1[i],data2[i]),round);
			}
		}
		return r;
	}
		
	// 求最大值
	public static double Max(double... values) {
		double r = values[0];
		for (double d : values) {
			if (d > r) {
				r = d;
			}
		}
		return r;
	}

	// 求数组中最大值
	public static double[] Max(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < r.length; i++) {
			r[i] = data1[i] > data2[i] ? data1[i] : data2[i];
		}
		return r;
	}

	// 求数组中最小值  add by liss 2014-09-10
	public static double[] Min(double[] data1, double[] data2) {
		double[] r = new double[data1.length];
		for (int i = 0; i < r.length; i++) {
			r[i] = data1[i] < data2[i] ? data1[i] : data2[i];
		}
		return r;
	}
	/**
	 * 复制数组
	 * @param data
	 * @return
	 */
	public static double[] copy(double[] data) {
		double[] r = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			r[i] = data[i];
		}
		return r;
	}

	/**
	 * 复制数组
	 * @param data
	 * @return
	 */
	public static double[][] copy(double[][] data) {
		double[][] r = new double[data.length][data[0].length];
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
			r[i] = data;
		}
		return r;
	}

	/**
	 * 把夏天的值 复位0 (6 - 9)为夏天
	 * 
	 * @return
	 */
	public static double[] summer(double[] data) {
		double[] r = F_Father.copy(data);
		r[5] = 0;
		r[6] = 0;
		r[7] = 0;
		r[8] = 0;
		return r;
	}

	public static double[] setSummer(double[] data, double val) {
		double[] r = F_Father.copy(data);
		r[5] = val;
		r[6] = val;
		r[7] = val;
		r[8] = val;
		return r;
	}

	/**
	 * 把冬天的值 复位0 (1 - 5 , 10 -12)为冬天
	 * 
	 * @return
	 */
	public static double[] winter(double[] data) {
		double[] r = F_Father.copy(data);
		r[0] = 0;
		r[1] = 0;
		r[2] = 0;
		r[3] = 0;
		r[4] = 0;
		r[9] = 0;
		r[10] = 0;
		r[11] = 0;
		return r;
	}

	public static double[] setWinter(double[] data, double val) {
		double[] r = F_Father.copy(data);
		r[0] = val;
		r[1] = val;
		r[2] = val;
		r[3] = val;
		r[4] = val;
		r[9] = val;
		r[10] = val;
		r[11] = val;
		return r;
	}

	/**
	 * 夏天的效率设为100% （计算热水效率的时候使用）
	 * 
	 * @param data
	 * @return
	 */
	public static double[] summer100(double[] data) {
		double[] r = F_Father.copy(data);
		r[5] = 100;
		r[6] = 100;
		r[7] = 100;
		r[8] = 100;
		return r;
	}

	/**
	 * 判断一个数组中数是否全部相等
	 * 
	 * @return
	 */
	public static boolean eq(double[] data) {
		double r = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] != r) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 是否 包含 val
	 */
	public static boolean in(int val, int... vals) {
		for (int i : vals) {
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
	 * val 中的数 全部在 vals 中
	 * 
	 * @param val
	 * @param vals
	 * @return
	 */
	public static boolean in(double[] val, double... vals) {
		for (int i = 0; i < val.length; i++) {
			boolean o = false;
			for (int a = 0; a < vals.length; a++) {
				if (val[i] == vals[a]) {
					o = true;
					break;
				}
			}
			if (!o) {
				return false;
			}
		}

		return true;
	}

	public static boolean in(double val, List<Integer> vals) {
		for (int a : vals) {
			if (a == val) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 内插法
	 */
	public static double interpolation(double X, double x1, double y1, double x2, double y2) {
//		double r =               y1 + (y2 - y1)     /      (x2 - x1)     *   (X - x1) ;
		double r = sumAll(y1,mul(div(subAll(y2,y1),subAll(x2,x1)), subAll(X,x1)));
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
			return div(interpolation(X, data[c[0]][C1], data[c[0]][C2], data[c[1]][C1], data[c[1]][C2]),1);
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
			//return F.f4(1/interpolation(X, data[c[0]][C1], 1/data[c[0]][C2], data[c[1]][C1], 1/data[c[1]][C2]));
			return div(1,interpolation(X, data[c[0]][C1], div(1,data[c[0]][C2]), data[c[1]][C1], div(1,data[c[1]][C2])));
		}
	}
	/**
	 * 查找 data中 和 val 相同的
	 * 
	 * @param val
	 * @param data
	 * @return
	 */
	public static int eq(double val, double[] data) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == val) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 查找和数组中最相近的两列值
	 * 
	 * @return
	 */
	// public static int[] close(double val, double[] data){
	// double c = Math.abs(data[0] - val);
	// int[] r = new int[2];
	// for(int i = 0 ; i < data.length ;i++ ){
	// if(Math.abs(data[i] - val) < c){
	// //不考虑 0 情况
	// if(data[i]!=0){
	// r[0] = i;
	// c = Math.abs(data[i] - val);
	// }
	// }
	// }
	// //查找第一个数 不等于上面的数 且不等于0
	// r[1] = -1;
	// for(int i = 0 ; i < data.length ; i++){
	// if(i!=r[0]&&data[i]!=0){
	// r[1] = i;
	// break;
	// }
	// }
	// c = Math.abs( data[r[1]] - val);
	// for(int i = r[1] ; i < data.length ;i++ ){
	// if(i == r[0]){
	// continue;
	// }
	// if(Math.abs(data[i] - val) < c){
	// if(data[i]!=0){
	// r[1] = i;
	// c = Math.abs( data[r[1]] - val);
	// }
	// }
	// }
	// return r;
	// }
	// 默认是从小到大进行排序的 查找的值
	public static int[] close(double val, double[] data) {
		int index = 0;
		for (int i = 0; i < data.length - 1; i++) {
			if (val > data[i] && val < data[i + 1]) {
				index = i;
				break;
			}
		}
		return new int[] { index, index + 1 };
	}

	/**
	 * c = 列数 把一维数组转换成二维数组
	 */
	public static double[][] change(double[] data, int C) {
		int R = data.length % C == 0 ? data.length / C : data.length / C + 1;
		double[][] r = new double[R][C];
		int r1 = 0;
		int c1 = 0;
		for (int i = 0; i < data.length; i++) {
			r[r1][c1] = data[i];
			c1++;
			if ((i + 1) % C == 0 && i > 0) {
				r1++;
				c1 = 0;
			}
		}
		return r;
	}

	// 改变一列值
	public static double[][] set(double data[][], double val, int C) {
		double[][] r = copy(data);
		for (int i = 0; i < r.length; i++) {
			r[i][C] = val;
		}
		return r;
	}

	// 替换一列值
	public static double[][] replace(double data[][], double oldv, double newv, int C) {
		double[][] r = copy(data);
		for (int i = 0; i < r.length; i++) {
			if (r[i][C] == oldv) {
				r[i][C] = newv;
			}
		}
		return r;
	}

	/**
	 * 求平均值（不包括0）
	 * @param data
	 * @return
	 */
	public static double aveNot0(double[] data) {
		double v = sum(data);
		double n = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] != 0) {
				n++;
			}
		}
		if(n==0)
			return 0;
		return div(v,n);
	}

	/**
	 * 获取一个数组 indexS, indexE 行 不包括indexE
	 * 
	 * @return
	 */
	public static double[][] get(int indexS, int indexE, double[][] data) {
		double[][] r = new double[indexE - indexS][data[0].length];
		for (int a = 0; a < r.length; a++) {
			for (int b = 0; b < r[0].length; b++) {
				r[a][b] = data[a + indexS][b];
			}
		}
		return r;
	}
	
	//比较HLP
	public static double[] compareVal(double[] data,double val){
		double[] data2=new double[data.length];
		for (int i=0;i<data.length;i++) {
			if(data[i]> val){
				data2[i] = 6.0;
			}else{
				data2[i] =data[i];
			}
		}
		return data2;
	}
	
	/*
	 * 获取一个数组第 index元素
	 * zhanghuihua
	 */
//	public static String get(int index, String data[]){
//		if (index<0 || index>=data.length){
//			return "";
//		}
//		
//		return data[index];
//	}
	
	/*
	 * 获取一个数组第 index元素, 是否包含substr
	 * zhanghuihua
	 */
	public static boolean contains(String data[], int index, String substr) {
		if (index>=0 && index<data.length && substr != null){
			String str = data[index];
			if (str != null && str.indexOf(substr) != -1){
				return true;
			}
		}
		
		return false;
	}
}
