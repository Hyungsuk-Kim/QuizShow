package quiz.common.helper;

/** java.util.Date와 java.sql.Date 간의 type 변경을 위한 methods를 제공하는 클래스*/
public class ConvertDateType {
	
	/** java.util.Date -> java.sql.Date */
	public static java.sql.Date ConvertDateUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = null;
		if (uDate != null) {
			sDate = new java.sql.Date(uDate.getTime());
		}
		return sDate;
	}
	
	/** java.sql.Date -> java.util.Date */
	public static java.util.Date ConvertDateSqlToUtil(java.sql.Date sDate) {
		java.util.Date uDate = null;
		if (sDate != null) {
			uDate = new java.util.Date(sDate.getTime());
		}
		return uDate;
	}
	
	/*public static void main(String[] args) {
		java.util.Date uDate = new java.util.Date();
		java.util.Date uDateTemp = null;
		java.sql.Date sDate = new java.sql.Date(2015, 8, 23);
		java.sql.Date sDateTemp = null;
		System.out.println(ConvertDateSqlToUtil(sDate).toString());
		System.out.println(ConvertDateUtilToSql(uDate).toString());
		sDateTemp = ConvertDateUtilToSql(uDate);
		System.out.println("sDateTemp : " + sDateTemp.toString());
		uDateTemp = ConvertDateSqlToUtil(sDate);
		System.out.println("uDateTemp : " + uDateTemp.toString());
	}*/
}
