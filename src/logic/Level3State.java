package logic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import TileMap.*; //para que cargue el mapa
import entities.*;
import entities.Enemies.Bomber;
import entities.Enemies.Boss1;
import entities.Enemies.Copter;
import entities.Enemies.Fire;
import entities.Enemies.FireballGetter;
import entities.Enemies.Heart;
import entities.Enemies.Relic;
import entities.Enemies.Slugger;
import entities.Enemy;
import gui.GamePanel;
import gui.GameState;
import gui.GameStateManager;
import gui.HUD;
import gui.HUD2;

public class Level3State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private Boss1 boss;
	
	private ArrayList<Player> players;
	private ArrayList<Enemy> enemies;
	private ArrayList<Enemy> enemies2;
	private ArrayList<Enemy> enemies4;
	private ArrayList<Boss1> enemiesboss;
	
	private ArrayList<Explosion> explosions;
	private ArrayList<Relic> relics;
	private ArrayList<Heart> hearts;
	private ArrayList<FireballGetter> getters;
	private ArrayList<Fire> fires;
	
	
	private HUD hud; //Vida y Batería
	private HUD2 hud2;//Reliquias, Tiempo, Puntos
	
	
	public Level3State(GameStateManager gsm, int inhealth, int inscore){
		this.gsm = gsm;
		init(inhealth, inscore);
	}
	
	public void init(int inhealth, int inscore){
		
		tileMap = new TileMap(30);
		tileMap.loadTiles(ReadProperties.file.getSetting("tiles1"));
		tileMap.loadMap(ReadProperties.file.getSetting("map3"));
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		bg = new Background(ReadProperties.file.getSetting("imgmenu"),1);
		bg.setVector(0.01,0);//Movimiento a la derecha
		
		player = new Player(tileMap,inhealth,inscore); //POSICION INICIAL DEL JUGADOR
		player.setPosition(
				Double.parseDouble(ReadProperties.file.getSetting("initx")), //Conversio de
				Double.parseDouble(ReadProperties.file.getSetting("inity")) //String a double
				);
		
		//Instancia los enemigos, reliquias y vidas adicionales
		populateStuff();
		explosions = new ArrayList<Explosion>(); //Array para las explosiones
		
		//Instacia el HUD; para poder visualizar Vida & Poder del jugador
		hud = new HUD(player);
		//Instancia el HU2; para poder visualizar tiempo,reliquias, y puntos.
		hud2= new HUD2(player);

	}
	private void populateStuff(){
		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy>();
		enemies4 = new ArrayList<Enemy>();
		enemiesboss = new ArrayList<Boss1>();
		relics = new ArrayList<Relic>();
		hearts = new ArrayList<Heart>();
		fires = new ArrayList<Fire>();
		getters = new ArrayList<FireballGetter>();
		players = new ArrayList<Player>();
		
		//----------------------COORDENADAS DE OBJETOS--------------------------------//
		
		//COORDENADAS DE FIRES
		Fire f;
		Point[] points4 = new Point[]{
				new Point(2565,196),
				new Point(2922,196),
				new Point(3018,106)
		};
		for(int i = 0; i < points4.length; i++){
			f = new Fire(tileMap);
			f.setPosition(points4[i].x,points4[i].y);
			fires.add(f);
		}
		
		//COORDENADAS DE CORAZONES
		Heart h;
		Point[] points3 = new Point[]{
				new Point(200,185)

				
		};
		for(int i = 0; i < points3.length; i++){
			h = new Heart(tileMap);
			h.setPosition(points3[i].x,points3[i].y);
			hearts.add(h);
		}
		//COORDENADAS DE FIREBALL GETTERS
		FireballGetter fbg;
		Point[] pointsfbg = new Point[]{
				new Point(1055,40)

		};
		for(int i = 0; i < pointsfbg.length; i++){
			fbg = new FireballGetter(tileMap);
			fbg.setPosition(pointsfbg[i].x,pointsfbg[i].y);
					getters.add(fbg);
		}
		//COORDENADAS DE RELIQUIAS
		Relic r;
		Point[] points2 = new Point[]{
				new Point(625,65),
				new Point(3513,50),
				new Point(4087,90),
				

		};
		for(int i = 0; i < points2.length; i++){
			r = new Relic(tileMap);
			r.setPosition(points2[i].x,points2[i].y);
			relics.add(r);
		}
		//COORDENADAS DE SLUGGER - ENEMIGO TIPO 1
		Slugger s;
		Point[] points = new Point[]{
				new Point(210,90),
				new Point(35,90),
				new Point(703,150),
				new Point(3130,160),
				new Point(3279,70),
				new Point(2003,180),
				new Point(240,180)

		};
		for(int i = 0; i < points.length; i++){
			s = new Slugger(tileMap);
			s.setPosition(points[i].x,points[i].y);
			s.setLeft(true);
			enemies.add(s);
		}
		players.add(player);
		//COORDENADAS DE BOMBERS - ENEMIGO TIPO 2
		Bomber b;
		Point[] pointsb = new Point[]{
				new Point(625,65),
				new Point(3137,160),
				new Point(3279,70)

		};
		for(int i = 0; i < pointsb.length; i++){
			b = new Bomber(tileMap);
			b.setPosition(pointsb[i].x,pointsb[i].y);
			enemies2.add(b);
		}
		
		//COORDENADAS DE COPTERS - ENEMIGO TIPO 3
		Copter c;
		Point[] pointsc = new Point[]{
				new Point(794,110),
				new Point(972,180),
				new Point(972,40),
				new Point(1314,200),
				new Point(1350,40),
				new Point(1375,80),
				new Point(1290,110),
				new Point(1400,180),
				new Point(1695,40),
				new Point(1695,80),
				new Point(1730,110),
				new Point(1650,180),
				new Point(1779,170)


		};
		for(int i = 0; i < pointsc.length; i++){
			c = new Copter(tileMap);
			c.setPosition(pointsc[i].x,pointsc[i].y);
			c.setStartX(pointsc[i].x-50);
			c.setStartY(pointsc[i].y);
			if (i % 2 == 0){
				c.setStLEFT();
			}
			else{
				c.setStRIGHT();
			}
			enemies4.add(c);
		}

	}
	//----------------------INSTANCIACION DEL JEFE DE NIVEL--------------------------------//
	public void instanceTempBombers(){
		Bomber b;
		Point[] pointsb = new Point[]{
				new Point(1950,40),
				new Point(1960,40)

		};
		for(int i = 0; i < pointsb.length; i++){
			b = new Bomber(tileMap);
			b.setPosition(pointsb[i].x,pointsb[i].y);
			
			if (i % 2 == 0){
				b.setLeft(true);
			}
			else{
				b.setRight(true);
			}
			enemies4.add(b);
			enemies2.add(b);
			
		}
	}
	public void instanceBoss(){
		Boss1 bo1;
		Point[] pointsbo1 = new Point[]{
				new Point(3900,110)

		};
		for(int i = 0; i < pointsbo1.length; i++){
			bo1 = new Boss1(tileMap,player);
			bo1.setPosition(pointsbo1[i].x,pointsbo1[i].y);
			bo1.setStartX(pointsbo1[i].x-50);
			bo1.setStartY(pointsbo1[i].y);
			if (i % 2 == 0){
				bo1.setStLEFT();
			}
			else{
				bo1.setStRIGHT();
			}
			enemiesboss.add(bo1);
			boss=bo1;
		}
	}
	//CIERRE DE LA SALIDA AL LLEGAR A LA HABITACION DEL JEFE
	public void closeExit(){
		Fire f;
		Point[] points4 = new Point[]{
				new Point(3570,200),
				new Point(3540,200),
				new Point(3510,200),
				new Point(3480,200),
				new Point(3450,200),
				new Point(3420,200),
				new Point(3390,200),
				new Point(3360,200),
				new Point(3330,200),
				new Point(3556,106),
				new Point(3526,106),
				new Point(3496,106),
				new Point(3466,106)
				
		};
		for(int i = 0; i < points4.length; i++){
			f = new Fire(tileMap);
			f.setPosition(points4[i].x,points4[i].y);
			fires.add(f);
		}
		
	}
	/////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////METODO UPDATE/////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////
	public void update(){
		bg.update();
		if(Boss1.getlivs() <=0){
			player.setBossKill();
		}
		//CONDICION DE MUERTE DEL JUGADOR
		if(player.getHealth() <= 0 || player.getTime() <= 0){
			SoundGame.music.stop();
			gsm.setState(GameStateManager.GAMEOVERSTATE,0,0);
		}
		if(player.getRelics() >= 3 && player.getbossKill() >= 3){
			SoundGame.music.stop();
			int temp;
			temp = (int)player.getTime()/60;
			gsm.setState(GameStateManager.ENDSTATE,player.getHealth(),player.getScore()+temp);
		}
		//JUGADOR TOCA EL BORDE SUPERIOR
		if(player.gety() <= 2){
			explosions.add(new Explosion(player.getx(), player.gety())); //Añade explosion
			player.sety(player.gety()+40);
			player.setHealth(player.getHealth() - 1);
		}
		//JUGADOR TOCA EL BORDE INFERIOR
		if(player.gety() >= 220){
			explosions.add(new Explosion(player.getx(), player.gety())); //Añade explosion
			player.setHealth(player.getHealth() - 1);
			if(player.getx() >= 1060 && player.getx()<= 1875){
				player.setx(1050);
				player.sety(180);
			}
			else if(player.getx() >= 2175 && player.getx()<= 3223){
				player.setx(2175);
				player.sety(160);
			}
			else{
				player.setx(player.getx()-150);
				player.sety(player.gety()-50);
			}

		}
		//CIERRE DE SALIDA E INSTANCIACION DEL JEFE DE NIVEL
		if (player.getx() >= 3600){
			player.setExitCounter(1);
			if(player.getexitCounter() >= 1 && player.getexitCounter() <3){
				closeExit();
				instanceBoss();
			}
		}
		if(player.getx() >= 3600){
			boss.update();
		}
		//INstanciacion de Bombers Especiales
		if (player.getx() >= 1876){
			if(player.getexitCounter() == 0){
				instanceTempBombers();
				player.setExitCounter(1);
			}
			
		}
		
		player.update();
		tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT /2 - player.gety()
				);

		///////////////////////////////////////////////////////////////////////////////////
		//VERIFICACION DE LOS ATAQUES HACIA LOS DIFERENTES ENEMIGOS
		player.checkAttack(enemies,1);
		player.checkAttack(enemies2,2);
		player.checkAttack(enemies4,2);
		player.checkAttackBoss(enemiesboss,1);
		player.checkCapture(relics);
		player.checkHearts(hearts);
		player.checkFire(fires);
		player.checkGetters(getters);
		Boss1.checkAttack(players);
		//////////////////////////////////////////////////////////////////////////////////
		
		//################################################################################
		//###################### ACTUALIZACION DE OBJETOS  ###############################
		//################################################################################
		
		//Actualizar Fires
		for(int i = 0; i < fires.size(); i++){
			Fire f = fires.get(i);
			f.update();
			if(f.isDead()){
				fires.remove(i);
				i--; //QUitar al enemigo del array
				explosions.add(new Explosion(f.getx(), f.gety())); //Añade explosion
																		   //al chocar.
				player.setScore(500);//Añade Puntos por apagar el fuego.
			}
		}
		//Actualizar Corazones Adicionales
		for(int i = 0; i < hearts.size(); i++){
			Enemy e = hearts.get(i);
			e.update();
			if(e.isTook()){
				hearts.remove(i);
				i--;
				if (player.getHealth() >= 5){//Si tiene 3 vidas no se le dan más
					player.setHealth(5);
					player.setScore(250);
				}
				else{
					player.setHealth(player.getHealth()+1);
				}
			}
		}
		//Actualizar FireballGetters Adicionales
		for(int i = 0; i < getters.size(); i++){
			Enemy e = getters.get(i);
			e.update();
			if(e.isTook()==true){
				getters.remove(i);
				i--;
				if (Player.getFireType() == 2){
					return;
				}
				else{
					player.setFireType(2);
					player.setFireBallDamage(15); //NUEVO DAÑO DE LA BALA
				}
			}
		}
		//Actualizar Reliquias
		for(int i = 0; i < relics.size(); i++){
			Enemy e = relics.get(i);
			e.update();
			if(e.isTook()){
				relics.remove(i);
				i--; //QUitar al enemigo de la GUI y además lo remuves del array
				player.setScore(400);//Añade Puntos por recupera la reliquia
			}
		}
		//Actualizar Enemigos 1
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			if (e.gety() >= 225){
				enemies.remove(i);
			}
			e.update();
			if(e.isDead()){
				enemies.remove(i);
				i--; //QUitar al enemigo del array
				explosions.add(new Explosion(e.getx(), e.gety())); //Añade explosion
																   //al chocar.
				player.setScore(50);//Añade Puntos por matar el enemigo
			}
		}
		//Actualizar Enemigos 2
		for(int i = 0; i < enemies2.size(); i++){
			Enemy e = enemies2.get(i);
			if (e.gety() >= 225){
				enemies.remove(i);
			}
			e.update();
			if(e.isDead()){
				enemies2.remove(i);
				i--; //QUitar al enemigo del array
				explosions.add(new Explosion(e.getx(), e.gety())); //Añade explosion
																  //al chocar.
				player.setScore(50);//Añade Puntos por matar el enemigo
			}
		}
		//Actualizar Enemigos 4
		for(int i = 0; i < enemies4.size(); i++){
			Enemy e = enemies4.get(i);
			if (e.gety() >= 225){
				enemies.remove(i);
			}
			e.update();
			if(e.isDead()){
				enemies4.remove(i);
				i--; //QUitar al enemigo del array
				explosions.add(new Explosion(e.getx(), e.gety())); //Añade explosion
																   //al chocar.
				player.setScore(25);//Añade Puntos por matar el enemigo
			}
		}
		//Actualizar JEFE FINAL
		for(int i = 0; i < enemiesboss.size(); i++){
			Enemy e = enemiesboss.get(i);
			if (e.gety() >= 225){
				enemies.remove(i);
			}
			e.update();
			if(e.isDead()){
				enemiesboss.remove(i);
				i--; //QUitar al enemigo del array
				explosions.add(new Explosion(e.getx(), e.gety())); //Añade explosion
																   //al chocar.
				player.setScore(25);//Añade Puntos por matar el enemigo
			}
		}
		//Acualizar Explisiones
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i); //Para que luego de crear la explosion la elimne
				i--;
			}
		}
	}
	//################################################################################
	//###################### DIBUJO DE OBJETOS EN GUI  ###############################
	//################################################################################
	public void draw(Graphics2D g){
		//Dibujar Mapa
		bg.draw(g);
		//Dibujar TileMap
		tileMap.draw(g);
		
		//Dibujar Jugador
		player.draw(g);
		
		//Dibuja Fuegos
		for (int i = 0; i < fires.size(); i++){
			fires.get(i).draw(g);
		}
		//Dibuja Corazones
		for (int i = 0; i < hearts.size(); i++){
			hearts.get(i).draw(g);
		}
		//Dibuja FireballGetters
		for (int i = 0; i < getters.size(); i++){
			getters.get(i).draw(g);
		}
		//Dibuja Reliquias
		for (int i = 0; i < relics.size(); i++){
			relics.get(i).draw(g);
		}
		//Dibuja Enemigos 1
		for (int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		//Dibuja Enemigos 2
		for (int i = 0; i < enemies2.size(); i++){
			enemies2.get(i).draw(g);
		}
		//Dibuja Enemigos 4
		for (int i = 0; i < enemies4.size(); i++){
			enemies4.get(i).draw(g);
		}
		//Dibuja JEFE FINAL
		for (int i = 0; i < enemiesboss.size(); i++){
			enemiesboss.get(i).draw(g);
		}
		//Dibujar explosion
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		//Dibujar el HUD
		hud.draw(g);
		hud2.draw(g);
		
	}
	
	
	//################################################################################
	//######################    DETECCION DE TECLAS    ###############################
	//################################################################################
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_H) player.setHealth(10);
		
		
		if (k == KeyEvent.VK_LEFT) player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT) player.setRight(true);
		if (k == KeyEvent.VK_UP) player.setUp(true);
		if (k == KeyEvent.VK_DOWN) player.setDown(true);
		if (k == KeyEvent.VK_W) player.setJumping(true);
		if (k == KeyEvent.VK_E) player.setGliding(true);
		if (k == KeyEvent.VK_R) player.setScratching();
		if (k == KeyEvent.VK_F) player.setFiring();
		if (k == KeyEvent.VK_F1){
			int reply = JOptionPane.showConfirmDialog(null,"Exit to Main Menu?","Confirmation Dialog", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION) {
	        	SoundGame.music.stop();
	        	SoundMenu.music.start();
	        	gsm.setState(GameStateManager.MENUSTATE,0,0);
	        }
	        else {
	           return;
	        }
		}
	}
	public void keyReleased(int k){
		if (k == KeyEvent.VK_RIGHT) player.setRight(false);
		if (k == KeyEvent.VK_LEFT) player.setLeft(false);
		if (k == KeyEvent.VK_UP) player.setUp(false);
		if (k == KeyEvent.VK_DOWN) player.setDown(false);
		if (k == KeyEvent.VK_W) player.setJumping(false);
		if (k == KeyEvent.VK_E) player.setGliding(false);
	}
	

}