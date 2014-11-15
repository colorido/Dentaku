import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//後々リファクタリングするべき変数には後ろに_rを付ける
//最後に検索をかけて全部修正する
//未実装の機能についてのコメントは//rで

public class UserInterface {

	Calclator maincalclator;

	public static void main(String []args){
		System.out.println("PROGRAM START");
		UserInterface userinterface=new UserInterface();
		userinterface.startProgram();
	
	}
	public void startProgram(){
		System.out.println("数式を入力してください");
		BufferedReader systeminputbuffer = new BufferedReader(new InputStreamReader(System.in));
		String code="";
		try {
			code = systeminputbuffer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//String code = "2+(11*22+33)";
		System.out.println("Code:"+code +"\n");
		maincalclator = new Calclator();
		maincalclator.setCode(code);
		double answer=maincalclator.runningProgram();
		System.out.println("\n答え:"+answer);
	}
}
