import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



//仕様1のプログラム
public class CalclationProgram1 {
	//エントリーポイント
	public static void main(String[] args) {
		CalclationProgram1 calclater = new CalclationProgram1();
	}
	//コンストラクタ
	CalclationProgram1(){
		input();
	}
	//入力部
	void input(){
		System.out.println("PROGRAM START");

		BufferedReader systeminputbuffer;

		String stringnumber[]	=	new String[2];//標準入力の文字が入る
		Double number[]		=	 new Double[2];//標準入力の文字を数値化したものが入る

		for(int index =0; index < 2; index++){
			systeminputbuffer	=	new BufferedReader(new InputStreamReader(System.in));
			try {
				stringnumber[index]	=	systeminputbuffer.readLine();
				number[index]	=	Double.parseDouble(stringnumber[index]);
			} catch (IOException e) {// IOエラー
				e.printStackTrace();
			} catch (Exception e){ //数字じゃないエラー
				System.out.println("ERROR");
				return ;
			}
		}
		calclation(number[0],number[1]);
	}
	//計算部
	void calclation(double firstnumber,double secondnumber){
		String plus		=	""	+	(firstnumber+secondnumber);
		String minus	=	""	+	(firstnumber-secondnumber);
		String multiple	=	""	+	(firstnumber*secondnumber);
		String division	=	""	+	(firstnumber/secondnumber);
		if((firstnumber*secondnumber)	==	0){//ゼロ除算のキャッチ
			division	=	"ERROR";
		}
		System.out.printf("和 = %s,差 = %s,積 = %s,商 = %s",plus,minus,multiple,division);



	}

}
