
public class ExampleCode {

	public static void main(String[] args) {
		ExampleCode t =new ExampleCode();


	}
	String output="";
	public ExampleCode(){
		String line = "1234*5+678";

		int minpriority=2;
		for(int i=0;i<line.length();i++){

			viewall();
			int priority=getpriority(line.charAt(i));

			if(priority<minpriority){
				//取り出す
				for(;;){
					if(view()==255){
						//取り出せなかった
						break;
					}else{
						//System.out.println(output);
						output+=""+put();
						System.out.println("output:"+output);
					}
				}
				output+=",";
			}else{
				push(line.charAt(i));
			}
		}

	}


	char line[]=new char[256];
	int head=-1;
	public int getpriority(char a){
		int priority=0;
		switch (a){
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			priority=10;
			break;
		case '+':
		case '-':
			priority=1;
			break;
		case '*':
		case '/':
			priority=2;
			break;
		}
		return priority;
	}
	public void viewall(){
		for(int i=0;i<line.length;i++){
			System.out.print(""+line[i]);
		}
		System.out.println();
	}
	public void push(char num){
		head++;
		line[head]=num;
	}
	public char view(){
		if(head<0){
			return (char) 255;
		}
		return line[head];
	}
	public char put(){

		if(head<0){
			return (char) 255;
		}
		head --;
		return line[head+1];
	}


}
