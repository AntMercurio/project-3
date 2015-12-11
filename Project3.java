///Anthony Mercurio project 3
String name= "Anthony Mercurio";
String title= "Project 3";

//Instantiating objects
Ball b1,b2,b3,b4,b5,cue;
Button resetbut,wallbut,ratbut,birdbut;
Bird bird;
Rat rat;

//GLOBAL VARIABLES
float right,left,top,bottom;
float center;

int wall=1;
int score=0;

boolean fly=false;
boolean bomb=false;

//Basic Setup
void setup(){
  size(800,600);
  right=width*9/10;
  left=width/10;
  top=height*2/5;
  bottom=height*9/10;
  center= width/2;
  b1=new Ball();
  b2=new Ball();
  b3=new Ball();
  b4=new Ball();
  b5=new Ball();
  cue=new Ball();
  resetbut=new Button(300,50,"Reset");
  ratbut=new Button(300,125,"Wall");
  birdbut=new Button(425,50,"Rat");
  wallbut=new Button(425,125,"Bird"); 
  bird=new Bird();
  rat=new Rat();
  reset();
}

//Reset Everything
void reset() {
  b1.reset(center,right,3);
  b2.reset(center,right,3);
  b3.reset(center,right,3);
  b4.reset(center,right,3);
  b5.reset(center,right,3);
  cue.reset(left,center,0);
  score=0;
  
}

//Call all modules and objects
void draw() {
  scene();              
  table();              
  balls();              
  buttons();            
  bird();               
  rat();                
  messages();           
}

//Background and etc.
void scene() {
  float x=0;
  background(100,150,200);
  strokeWeight(4);
  stroke(0,255,0);
  while(x<width) {      //Grass
     line(x,height-50,x,height);
     x+=10;
  }
  strokeWeight(1);
  stroke(0);
}

//Green Table with brown border
void table() {
  rectMode(CORNERS);
  fill(10,200,100);
  strokeWeight(20);
  stroke(127,0,0);
  rect(left,top,right,bottom);
  if (wall > 0) {line(center,top,center,bottom);}
  if (wall > 1) {wall=0;}
  stroke(0);
  strokeWeight(1);
}

//Calling ball
void balls() {
  b1.show(255,0,0,"1");b1.move();      //Ball 1 modules
  b2.show(0,255,0,"2");b2.move();      //Ball 2 modules
  b3.show(0,0,255,"3");b3.move();      //Ball 3 modules
  b4.show(255,0,255,"4");b4.move();    //Ball 4 modules
  b5.show(0,255,255,"5");b5.move();    //Ball 5 modules
  cue.show(255,255,255,"");cue.move();//Ball cue modules
  
  //Elastic Collisions
  collision(b1,b2);  collision(b1,b3);  collision(b1,b4);
  collision(b1,b5);  collision(b1,cue);
  //////
  collision(b2,b3);  collision(b2,b4);  collision(b2,b5);
  collision(b2,cue);
  //////
  collision(b3,b4);  collision(b3,b5);  collision(b3,cue);
  //////
  collision(b4,b5);  collision(b4,cue); collision(b5,cue);
  
}

void collision( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;      // Swap the velocities.
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp;
    score+=1;
  }
}

//Visuals
void buttons(){
  resetbut.show();
  wallbut.show();
  ratbut.show();
  birdbut.show();
}

//Bird calling
void bird(){
  bird.bomb();
  bird.show();
}

void rat(){
  rat.show();
  rat.move();
  //if (rat.hit(b1)) {b1.dx=0;b1.dy=0;score+=50;}
  //if (rat.hit(b2)) {b2.dx=0;b2.dy=0;score+=50;}
  //if (rat.hit(b3)) {b3.dx=0;b3.dy=0;score+=50;}
  //if (rat.hit(b4)) {b4.dx=0;b4.dy=0;score+=50;}
  //if (rat.hit(b5)) {b5.dx=0;b5.dy=0;score+=50;}
  //if (rat.hit(cue)){cue.dx=0;cue.dy=0;score+=50;}
}

//Title, Name, Directions
void messages(){
  fill(0);
  textSize(30);
  text(title,100,50);
  text(name,100,100);
  textSize(10);
  text("Score: "+score,left,top-15);
}
//////BALL CLASS//////
class Ball {
  //Properties of the ball
  float x,y,dx,dy;
  float d=20;//diameter of ball
  
  //Constructors (if any)
  
  //METHODS//
  //draw the ball
  void show(float r, float g, float b, String name) {
    fill(r,g,b); ellipse(x,y,d,d);
    fill(0,0,0); text(name, x,y);
  }
  
  //make the ball move
  void move() {
    x+=dx; y+=dy;
    if (x>right-10-(d/2) || x<left+10+(d/2)) dx= -dx; //Bounce off walls
    if (y>bottom-10-(d/2) || y<top+10+(d/2)) dy= -dy;
    if (wall > 0 && x > center-10-(d/2) && x < center+10+(d/2)) dx= -dx;
  }
  
  //Reset Function (float starting x-locations on table)
  void reset(float location1, float location2, float ss) {
    x=random(location1+25,location2-25);     //x-position
    y=random(top+25,bottom-25);       //y-position
    dx=random(-ss,ss);dy=random(-ss,ss);  //movement
    //((ss("starting speed" is 3 for most balls but 0 for cue))
  }
  
  //hit method for collisions
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.x,this.y ) < d ) return true;
    else return false;
  }
}

/////BUTTON CLASS/////
class Button {
  //Properties
  float x,y;
  float w=100;
  float h=50;
  String name;
  //Constructors
  Button(float tempx,float tempy,String tempname){
    x=tempx;
    y=tempy;
    name=tempname;
  }
  //METHODS//
  //show Button
  void show() {
    rectMode(CENTER);
    fill(255);
    rect(x,y,w,h);
    fill(0);
    text(name,x-10,y+5);
  }
  //To Show if the button is clicked
  boolean hit(float x1,float y1,float x2,float y2,float w,float h){
    boolean result;
    if ( abs(x1-x2) < w && abs(y1-y2)<h ) {result=  true;} 
    else {result=false;}
    return result;
  }
}

/////BIRD CLASS///
class Bird {
  //Properties
  float x=-30,y=100,bomby=y,dx=0,dy=1;
  //Constructors
  
  //Methods
  //draw bird
  void show(){
  fill(255,200,0);
  triangle(x+30,y,  x-15,y-20, x-15,y+20);
  x+=dx;
  if (fly)        {dx=1;}
  if (x>width+30) {
    x=-30;
    dx=0;
    fly=false;
    bomb=false;
    bomby=y;
    }
  }
  
  //drop bomb on second button press
  void bomb(){
    fill(0);
    ellipse(x,bomby,10,25);
    bomby+=dy;
    dy*=1.01;
  }
  
  
}

/////RAT CLASS////
class Rat {
  //Properties
  float x=-50,y=height-100,dx,dy;
  float w=50,h=25;
  boolean run=false;
  
  //Constructors
  
  //Methods
  //draw the rat
  void show() {
     fill(200);
     rect(x,y,w,h);
  }
  //make the rat move across the screen
  void move() {
    if (run) {
      x+=dx;y+=dy;
      dx=random(0,3);
      dy=random(-3,3);
    }
    if (x>width+50) {
      run=false;
      x=-50;
    }
  }
  //detect and count hits between balls and rat
  boolean hit(Ball p){
    boolean result;
    if ( abs(p.x-this.x)<this.w && abs(p.y-this.y)<this.y) {result=true;}
    else {result=false;}
    return result;
  }
  
}

//////TEST HANDLERS
//MOUSECLICK TO TEST RESET
void mouseClicked(){
  if ( dist( mouseX,mouseY,b1.x,b1.y) < 30)   {b1.reset(center,right,3);score-=5;}
  if ( dist( mouseX,mouseY,b2.x,b2.y) < 30)   {b2.reset(center,right,3);score-=5;}
  if ( dist( mouseX,mouseY,b3.x,b3.y) < 30)   {b3.reset(center,right,3);score-=5;}
  if ( dist( mouseX,mouseY,b4.x,b4.y) < 30)   {b4.reset(center,right,3);score-=5;}
  if ( dist( mouseX,mouseY,b5.x,b5.y) < 30)   {b5.reset(center,right,3);score-=5;}
  if ( dist( mouseX,mouseY,cue.x,cue.y) < 30) {cue.reset(left,center,0);score-=5;}
  if ( resetbut.hit(mouseX,mouseY,resetbut.x,resetbut.y,resetbut.w,resetbut.h) ){reset();}
  if ( wallbut.hit(mouseX,mouseY,wallbut.x,wallbut.y,wallbut.w,wallbut.h) ){wall+=1;}
  if ( birdbut.hit(mouseX,mouseY,birdbut.x,birdbut.y,birdbut.w,birdbut.h) ){fly=true;}
  if ( ratbut.hit(mouseX,mouseY,ratbut.x,ratbut.y,ratbut.w,ratbut.h) ){rat.run=true;}
}

//KEYSTROKES TO TEST FUNCTIONS
void keyPressed(){
  if (key == 'w')        {wall+=1;}  //make the wall either appear or disappear
  if (key == 'r')        {reset();}  //reset entire screen
  if (key == '1')        {b1.reset(center,right,3);}
  if (key == '2')        {b2.reset(center,right,3);}
  if (key == '3')        {b3.reset(center,right,3);}
  if (key == '4')        {b4.reset(center,right,3);}
  if (key == '5')        {b5.reset(center,right,3);}
  if (key == '6')        {cue.reset(left,center,0);}
  if (key == 'b')        {fly=true;}
  if (key == 'x')        {bird.x=100;}
  if (key == 'R')        {rat.run=true;}
}
