import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
メモ
括弧用の優先度が正しく処理されているか？
 */

//構文解析部のエラー
class MyParseException extends Exception{
	private static final long serialVersionUID = 1L;
	MyParseException(String text){
		super(text);
	}
}

//字句解析で利用する
class Token{
	int attribute;//属性
	String entity;//内容物
	int priority;//優先度
	Token(int attribute,String entity){//旧プログラムコード用　今後削除するべき
		this.attribute = attribute;
		this.entity = entity;
		this.priority=0;
	}
	Token(int attribute,String entity,int priority){//プライオリティ付きコンストラクタ
		this.attribute = attribute;
		this.entity = entity;
		this.priority=priority;
	}
	void setPriority(int priority){//
		this.priority = priority;
	}
}

public class Calclator{
	//:Public
	Double answer = new Double(0);

	//計算式のセット
	public void setCode(String code){
		analyzeToken(code);
		parseToken();
	}

	//プログラムコードの実行部分
	public Double runningProgram(){
		System.out.println("\nRUNNNING PROGRAM:");
		Map<String,Double> variablelist=new HashMap<String,Double>();
		for(int index=0;index<fouraddressprogram.size();index++){
			Double number1=new Double(0);
			if(fouraddressprogram.get(index)[1].attribute==1){
				number1 =  Double.parseDouble(fouraddressprogram.get(index)[1].entity);
			}else if(fouraddressprogram.get(index)[1].attribute==10){
				number1 = variablelist.get(fouraddressprogram.get(index)[1].entity);
			}
			Double number2=new Double(0);
			if(fouraddressprogram.get(index)[3].attribute==1){
				number2 =  Double.parseDouble(fouraddressprogram.get(index)[3].entity);
			}else if(fouraddressprogram.get(index)[3].attribute==10){
				number2 = variablelist.get(fouraddressprogram.get(index)[3].entity);
			}
			Double resultnumber=new Double(0);
			if(fouraddressprogram.get(index)[2].entity.equals("+")){
				resultnumber = number1 + number2;
			}else if(fouraddressprogram.get(index)[2].entity.equals("-")){
				resultnumber = number1 - number2;
			}else if(fouraddressprogram.get(index)[2].entity.equals("*")){
				resultnumber = number1 * number2;
			}else if(fouraddressprogram.get(index)[2].entity.equals("/")){
				resultnumber = number1 / number2;
			}
			System.out.println("result:"+ resultnumber);
			answer=resultnumber;
			variablelist.put(fouraddressprogram.get(index)[0].entity,resultnumber);

		}
		return answer;
	}
	//構文解析部
	public void parseToken2(){
		//構文解析部
		Stack<Token> parsestack = new Stack<Token>();
		int	minpriority=-1;//-1は空
		for(int index=0;index<tokenlist.size();index++){
			int priority=tokenlist.get(index).priority;
			if((tokenlist.get(index).attribute==1) || (tokenlist.get(index).attribute==10) ){//数値または変数である
				parsestack.add(tokenlist.get(index));
			}else{//演算子
				if(minpriority == -1){
					minpriority = tokenlist.get(index).priority;
				}

				if(priority<minpriority){

				}

			}
		}
	}
	//構文解析部
	public void parseToken(){
		//構文解析部
		Stack<Token> parsestack = new Stack<Token>();
		int	minpriority=-1;//-1は空
		Token token;
		for(int index=0;index<tokenlist.size();index++){
			int priority=tokenlist.get(index).priority;
			token = tokenlist.get(index);
			if((tokenlist.get(index).attribute==1) || (tokenlist.get(index).attribute==10) ){//変数である
				parsestack.add(tokenlist.get(index));
			}else{
				//演算子の場合の処理
				//空
				if(minpriority==-1){
					//System.out.println("mintoken:"+tokenlist.get(index).entity);
					minpriority=tokenlist.get(index).priority;
				}
				if(priority>=minpriority){
					if(index<tokenlist.size()){
						parsestack.add(tokenlist.get(index));
					}
					minpriority = priority;
				}else{

					//取り出す処理//同じ処理が複数箇所に存在。要モジュール化検討
					for(int i=0;priority<minpriority && parsestack.size()>=3;i++){
						//int i=0;
						Token[] fourtoken = new Token[4];
						fourtoken[0]=new Token(10,"variable"+index+i,10);
						try{
							//取り出す処理 数字→演算子→数字
							Token pop1st =parsestack.pop();
							if(getPriority(pop1st)==10){//数字
								fourtoken[3]=pop1st;
								Token pop2nd =parsestack.pop();
								if(getPriority(pop2nd)!=10){//演算子
									fourtoken[2]=pop2nd;
									Token pop3rd =parsestack.pop();
									//System.out.println("pop3:"+pop3.entity);
									if(getPriority(pop3rd)==10){//数字
										fourtoken[1]=pop3rd;
									}else throw(new MyParseException("GrammerError code:3"));
								}else throw(new MyParseException("GrammerError code:2"));
							}else throw(new MyParseException("GrammerError code:1"));
						}catch(Exception e){
							System.out.println("文法がおかしい("+e.getMessage()+")");
						}
						fouraddressprogram.add(fourtoken);
						parsestack.add(fourtoken[0]);
						if(parsestack.size()>=3){
							minpriority = parsestack.get(parsestack.size()-2).priority;//tokenlist.get(index).priority;
						}else{
							//minpriority = -1;
						}
					}//for


					parsestack.add(tokenlist.get(index));
					minpriority = tokenlist.get(index).priority;

				}

			}

		}//for
		if(parsestack.size()>0){//全パース終了前にループを抜けてしまった場合の処理。

			//取り出す処理//同じ処理が複数箇所に存在。要モジュール化検討
			for(int i=0;parsestack.size()>=3;i++){

				Token[] fourtoken = new Token[4];
				fourtoken[0]=new Token(10,"variable"+"last"+i,10);
				try{
					//取り出す処理 数字→演算子→数字
					Token pop1st =parsestack.pop();
					if(getPriority(pop1st)==10){//数字
						fourtoken[3]=pop1st;
						Token pop2nd =parsestack.pop();
						if(getPriority(pop2nd)!=10){//演算子
							fourtoken[2]=pop2nd;
							Token pop3rd =parsestack.pop();
							if(getPriority(pop3rd)==10){//数字
								fourtoken[1]=pop3rd;
							}else throw(new MyParseException("GrammerError code:3"));
						}else throw(new MyParseException("GrammerError code:2"));
					}else throw(new MyParseException("GrammerError code:1"));
				}catch(Exception e){
					System.out.println("ERROR 文法がおかしい可能性がある("+e.getMessage()+")");
				}
				fouraddressprogram.add(fourtoken);
				parsestack.add(fourtoken[0]);
			}//for
		}
		viewFourprogram();

	}
	//字句解析部
	public void analyzeToken(String code){
		//字句解析部
		String	buffer="";
		int		brickcount=0;//括弧の量を
		int		attributehistory=0;//前回の解析した属性を記憶
		for(int index=0;index<code.length();index++){
			if( (attributehistory==0) || (attributehistory==getAttribute(code.charAt(index))) ){
				buffer+=""+code.charAt(index);
			}else{
				if(!(attributehistory==11 || attributehistory == 12)){
					tokenlist.add(new Token(attributehistory,buffer,getPriority(attributehistory)+(100*brickcount)));
				}
				buffer="";
				buffer+=""+code.charAt(index);
			}
			attributehistory=getAttribute(code.charAt(index));

			if(getAttribute(code.charAt(index))==12){// ')'
				--brickcount;
			}else if(getAttribute(code.charAt(index))==11){// '('
				++brickcount;
			}
		}
		if(!(attributehistory==11 || attributehistory == 12)){//最後の文字列が括弧だった時は処理をスキップ
			tokenlist.add(new Token(attributehistory,buffer,getPriority(attributehistory)));
		}
		viewTokens();
	}
	//四コードの表示
	public void viewFourprogram(){
		System.out.println("\nFourcodeView:");
		for(int index1=0;index1<fouraddressprogram.size();index1++){
			for(int index2=0;index2<fouraddressprogram.get(index1).length;++index2){
				System.out.print("["+fouraddressprogram.get(index1)[index2].entity+"]");
			}
			System.out.println();
		}
	}
	//トークンの表示
	public void viewTokens(){
		System.out.println("\nTokenView:");
		for(int index=0;index<tokenlist.size();index++){
			System.out.print("["+tokenlist.get(index).entity+":"+tokenlist.get(index).priority+"]");
		}
		System.out.println();
	}
	//演算子の属性
	public int getAttribute(char a){
		int attribute=0;
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
		case '.':
			attribute=1;
			break;
		case '+':
			attribute=2;
			break;
		case '-':
			attribute=3;
			break;
		case '*':
			attribute=4;
			break;
		case '/':
			attribute=5;
			break;
		case '(':
			attribute=11;
			break;
		case ')':
			attribute=12;
			break;
			/*
		 変数はattribute 10
			 */
		}
		return attribute;
	}
	public int getPriority(int arugment){
		int priority=0;
		switch (arugment){
		case 1://Number
		case 10:
			priority=10;
			break;
		case 2://+
		case 3://-
			priority=1;
			break;
		case 4://*
		case 5:// /
			priority=2;
			break;
			//カッコは　重複数*100　の重みをかけたプライオリティを使用すr
		}
		return priority;
	}
	public int getPriority(Token argument){
		int priority=0;
		return getPriority(argument.attribute);
	}
	//:Private
	ArrayList<Token> tokenlist=new ArrayList<Token>();
	ArrayList<Token[]> fouraddressprogram =new ArrayList<Token []>();
	Map<String,Integer> operatorPriority=new HashMap<String,Integer>();
}