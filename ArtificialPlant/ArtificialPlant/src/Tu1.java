
import java.awt.Color;
import java.awt.Component;
import java.util.Stack;

import ch.aplu.turtle.*;

class Tu1
{
  Turtle joe = new Turtle();
  String rule;
  boolean flag;
  

  Tu1(double angle, int distance, int max_it, String rl, String started)
  {
	  joe.speed(10000);
	  double line_width = 2;
	  joe.setPos(0, -150);
	  int r = 0 + (int)(Math.random()*255);
	  int g = 0 + (int)(Math.random()*255);
	  int b = 0 + (int)(Math.random()*255);
	  
	  Color color = new Color(r,g,b);
	  joe.setPenColor(color);
	 // joe.getPlayground().resize(1000, 1000);
	  Stack head = new Stack();
	  Stack pos = new Stack();
	  rule = GetReady(rl,started,max_it);
	  double x=0,y = 0;
	  for(int i=0;i<rule.length();i++)
	  {
		  System.out.println(rule.charAt(i));
		  if(rule.charAt(i)=='F')
		  {  
			  System.out.println("Heading="+joe.heading());
			  if(joe.heading()==0)
				  joe.setLineWidth(line_width);
	
			  else{
				  joe.setLineWidth((line_width/2)/(Math.ceil(Math.abs(joe.heading()))/Math.abs(angle)));
			  }
			  joe.forward(distance);
			  
		  }
		  if(rule.charAt(i)=='+')
			  joe.right(angle);
		  
		  if(rule.charAt(i)=='-')
			  joe.left(angle);
		
		  if(rule.charAt(i)=='['){
			
			  x = joe.getX();
			  y = joe.getY();
			  System.out.println("x="+x);
			  System.out.println("y="+y);
			  pos.push(x);
			  pos.push(y);
			  head.push(joe.heading());
			//  pos = joe.getPos();
		  }
		  if(rule.charAt(i)==']'){
			  //joe.setPos(pos);
			  double new_y = (double) pos.pop();
			  double new_x = (double) pos.pop();
			  joe.setPos(new_x, new_y);
			 joe.setHeading((double) head.pop());
			 
			 
		  }
		  
		  if(rule.charAt(i)=='C'){
			  r = 0 + (int)(Math.random()*255);
			  g = 0 + (int)(Math.random()*255);
			  b = 0 + (int)(Math.random()*255);
			  color = new Color(r,g,b);
			  joe.setPenColor(color);
			  
		  }
	  }
  }
  
  
  public String GetReady(String rl, String started_rule, int max_it){
	  
	  String rule;
	  
	  rl = rl.replaceAll(" ","");
	  int index = rl.indexOf('>');
	  rule = rl.substring(index+1);
	  
	  String ready_rule = started_rule;
	  //String ready_original = readyRule;
	  for(int i=0; i<max_it;i++){
		  
		  System.gc();
		  ready_rule = ready_rule.replaceAll("F",rule+"C");
		 
	  }
	  return ready_rule;
  }
  
}
















/*   joe.forward(100);
joe.right(90);
joe.forward(100);
joe.right(90);
joe.forward(100);
joe.left(90);
joe.forward(100);
joe.left(90);
joe.bk(20);
double x = joe.getX();
double y = joe.getY();
joe.forward(100);
joe.right(90);
joe.forward(100);
joe.right(90);
joe.setPos(x, y);*/
