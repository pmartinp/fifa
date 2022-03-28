package fifa;

import java.util.Random;
import java.util.Scanner;

public class FifaStreet {
	
	
	static String equipo1, equipo2, mvp;
	static String[][]jugadores1=new String[3][5];
	static String[][]jugadores2=new String[3][5];
	static int ataque, defensa, tiro, parada, goles1=0, goles2=0, turno=1, penalti=0, atacante, defensor, tirador, portero, porteropen1, porteropen2;
	static int contadorataques1=0, contadortiros1=0, contadorparadas1=0, contadorataques2=0, contadortiros2=0, contadorparadas2=0;
	static int[][]intervencionjugadores=new int[2][3];
	static String[][]jugadores=new String[2][3];
	static double valorataque, valordefensa, valortiro, valorparada;
	static Random r=new Random();
	static double n;
	static boolean ganar=false;
	static int tipopartido, numgoles, tiempopartido;
	static Tiempo tiempo = new Tiempo();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner leer= new Scanner(System.in);
		int selectormenu=0;
		boolean activo=true;
		
		while(activo==true) {
			
			menu();
			
			selectormenu=leer.nextInt();
			if(selectormenu<1 || selectormenu>6) {
				System.out.println("Esa opción no está disponible en el menú, porfavor elija una opción disponible");
				selectormenu=leer.nextInt();
			}
			
			switch(selectormenu) {
			
			//Crear equipo 1
			case 1:
				
				equipo1=crearEquipo(jugadores1);
				break;
				
			//Crear equipo 2
			case 2:
				
				equipo2=crearEquipo(jugadores2);
				break;
				
			//Mostrar equipos
			case 3:
				
				mostrarEquipo(equipo1, jugadores1);
				mostrarEquipo(equipo2, jugadores2);
				break;
				
			//Jugar partido
			case 4:
				
				jugarPartido();
				break;
				
			//Tanda de penaltis
			case 5:
				
				tandaPenaltis();
				break;
			
			//Salir del juego
			case 6:
				
				System.out.println("Mini FIFA Street. Juego realizado por Pedro Manuel Martín Pérez. 2021");
				activo=false;
				break;
			}
		}
		
	}
	
	static void menu () {		
		System.out.println("\r\nMenú");
		System.out.println("1. Crear equipo 1");
		System.out.println("2. Crear equipo 2");
		System.out.println("3. Mostrar equipos");
		System.out.println("4. Jugar partido");
		System.out.println("5. Tanda de penaltis");
		System.out.println("6. Salir del juego");

	}
	
	public static String crearEquipo(String[][]jugadores) {
		
		Scanner leer=new Scanner(System.in);
		
		System.out.println("Introduce el nombre del equipo");
		String equipo=leer.nextLine();
		
		jugadores[0]=crearJugador();
		jugadores[1]=crearJugador();
		jugadores[2]=crearJugador();
		
		return equipo;
		
	}
	
	public static String[] crearJugador() {
		
		Scanner leer=new Scanner(System.in);
		String []jugador=new String[5];
		
		//asignamos el nombre del jugador
		System.out.println("Introduce el nombre del jugador");
		String nomjugador=leer.nextLine();
		
		if(nomjugador.matches("[a-zA-Z0-9]*")) {
			jugador[0]=nomjugador;
		}else {
			System.out.println("El nombre introducido no es válido, por favor introduzca un nombre válido");
			return crearJugador();
		}
		
		//asignamos las características del jugador
		System.out.println("Rellene las características del jugador, como mínimo deben ser 15 y la suma de las 4 no pueden exceder los 100 puntos");
		System.out.print("Ataque: ");
		ataque=leer.nextInt();
		while(ataque<15 || ataque>55) {
			System.out.println("Valor no aceptado, por favor, introduzca un valor válido");
			System.out.print("Ataque: ");
			ataque=leer.nextInt();
		}
		int difataque=ataque-15;
		jugador[1]=String.valueOf(ataque);
		
		System.out.print("Defensa: ");
		defensa=leer.nextInt();
		while(defensa<15 || defensa>(55-difataque)) {
			System.out.println("Valor no aceptado, por favor, introduzca un valor válido");
			System.out.print("Defensa: ");
			defensa=leer.nextInt();
		}
		int difdefensa=defensa-15;
		jugador[2]=String.valueOf(defensa);
		
		System.out.print("Tiro: ");
		tiro=leer.nextInt();
		while(tiro<15 || tiro>(55-difataque-difdefensa)) {
			System.out.println("Valor no aceptado, por favor, introduzca un valor válido");
			System.out.print("Tiro: ");
			tiro=leer.nextInt();
		}
		int diftiro=tiro-15;
		jugador[3]=String.valueOf(tiro);
		
		System.out.print("Parada: ");
		parada=leer.nextInt();
		while(parada<15 || parada>(55-difataque-difdefensa-diftiro)) {
			System.out.println("Valor no aceptado, por favor, introduzca un valor válido");
			System.out.print("Parada: ");
			parada=leer.nextInt();
		}
		jugador[4]=String.valueOf(parada);
		
		
		return jugador;
	}
	
	public static void mostrarEquipo(String equipo, String[][]jugadores) {
		
		System.out.println("\r\n"+equipo+"\r\n");
		
		System.out.println("jugadores:");
		System.out.println(jugadores[0][0]+": "+ "AT "+jugadores[0][1]+", DF "+jugadores[0][2]+", TR "+jugadores[0][3]+", PA "+jugadores[0][4]);
		System.out.println(jugadores[1][0]+": "+ "AT "+jugadores[1][1]+", DF "+jugadores[1][2]+", TR "+jugadores[1][3]+", PA "+jugadores[1][4]);
		System.out.println(jugadores[2][0]+": "+ "AT "+jugadores[2][1]+", DF "+jugadores[2][2]+", TR "+jugadores[2][3]+", PA "+jugadores[2][4]);
	}
	
	public static void jugarPartido() {
		Scanner leer=new Scanner(System.in);
		
		goles1=0;
		goles2=0;
		ganar=false;
		turno=1;
		
		modoPartido();
		
		while(ganar==false) {
			
			comprobarTiempo();
			//ataque equipo1
			if(turno==1) {
				
				marcador();
				
				//seleccion atacante
				System.out.println("Ataca "+equipo1);
				System.out.println("Seleccione un jugador para atacar");
				mostrarEquipo(equipo1, jugadores1);
				atacante=leer.nextInt();
				
				
				intervencionjugadores[0][atacante-1]++;
				
				//seleccion defensa
				System.out.println("Defiende "+equipo2);
				System.out.println("Seleccione un jugador para defender");
				mostrarEquipo(equipo2, jugadores2);
				defensor=leer.nextInt();
				
				
				intervencionjugadores[1][defensor-1]++;
				
				calcularJugada(Integer.valueOf(jugadores1[atacante-1][1]),Integer.valueOf(jugadores2[defensor-1][2]),0);
				contadorataques1++;
				
				if(valordefensa>valorataque) {
					System.out.println("La defensa recupera el balón");
					turno=2;
				}else {
					//seleccion tirador
					System.out.println("El atacante ha regateado con éxito, seleccione un jugador para tirar");
					mostrarTirador(jugadores1);
					tirador=leer.nextInt();
					
					
					if(atacante==1) {
						tirador++;
					}else if(atacante==2) {
						if(tirador==2) {
							tirador++;
						}
					}
					intervencionjugadores[0][tirador-1]++;
					
					//seleccion portero
					System.out.println("Seleccione un portero");
					mostrarPortero(jugadores2);
					portero=leer.nextInt();
					
					
					if(defensor==1) {
						portero++;
					}else if(defensor==2) {
						if(portero==2) {
							portero++;
						}
					}
					intervencionjugadores[0][portero-1]++;
					
					calcularJugada(Integer.valueOf(jugadores1[tirador-1][3]),Integer.valueOf(jugadores2[portero-1][4]),1);
					contadortiros1++;
					
					if(valorparada>valortiro) {
						System.out.println("Paradooooon!!!");
						contadorparadas2++;
						turno=2;
					}else {
						System.out.println("Golazoooooo!!!");
						goles1++;
						
						if(tipopartido==1) {
							if(goles1==numgoles) {
								System.out.println("Ha ganado el "+equipo1);
								marcador();
								ganar=true;
							}else {
								turno=2;
							}
						}
					}
					
				}
			}
			
			comprobarTiempo();
			//ataque equipo 2
			if(turno==2) {
				
				marcador();
				
				//seleccion atacante
				System.out.println("Ataca "+equipo2);
				System.out.println("Seleccione un jugador para atacar");
				mostrarEquipo(equipo2, jugadores2);
				atacante=leer.nextInt();
				
				intervencionjugadores[1][atacante-1]++;
				
				//seleccion defensa
				System.out.println("Defiende "+equipo1);
				System.out.println("Seleccione un jugador para defender");
				mostrarEquipo(equipo1, jugadores1);
				defensor=leer.nextInt();
				
				intervencionjugadores[0][defensor-1]++;
				
				calcularJugada(Integer.valueOf(jugadores2[atacante-1][1]),Integer.valueOf(jugadores1[defensor-1][2]),0);
				contadorataques2++;
				
				if(valordefensa>valorataque) {
					System.out.println("La defensa recupera el balón");
					turno=1;
				}else {
					//seleccion tirador
					System.out.println("El atacante ha regateado con éxito, seleccione un jugador para tirar");
					mostrarTirador(jugadores2);
					tirador=leer.nextInt();
					
					if(atacante==1) {
						tirador++;
					}else if(atacante==2) {
						if(tirador==2) {
							tirador++;
						}
					}
					intervencionjugadores[1][tirador-1]++;
					
					//seleccion portero
					System.out.println("Seleccione un portero");
					mostrarPortero(jugadores1);
					portero=leer.nextInt();
					
					if(defensor==1) {
						portero++;
					}else if(defensor==2) {
						if(portero==2) {
							portero++;
						}
					}	
					intervencionjugadores[0][portero-1]++;
					
					calcularJugada(Integer.valueOf(jugadores2[tirador-1][3]),Integer.valueOf(jugadores1[portero-1][4]),1);
					contadortiros2++;
					
					if(valorparada>valortiro) {
						System.out.println("Paradooooon!!!");
						contadorparadas1++;
						turno=1;
					}else {
						System.out.println("Golazoooooo!!!");
						goles2++;
						
						if(goles2==numgoles) {
							System.out.println("Ha ganado el "+equipo2);
							marcador();
							ganar=true;
						}else {
							turno=1;
						}
					}	
					
				}
			}
		}
		mostrarEstadisticas();
	}
	
	public static void mostrarTirador(String[][]jugadores) {
		
		System.out.println("jugadores:");
		if(atacante==1) {
			System.out.println("1."+jugadores[1][0]+": "+ "AT"+jugadores[1][1]+", DF"+jugadores[1][2]+", TR"+jugadores[1][3]+", PA"+jugadores[1][4]);
			System.out.println("2."+jugadores[2][0]+": "+ "AT"+jugadores[2][1]+", DF"+jugadores[2][2]+", TR"+jugadores[2][3]+", PA"+jugadores[2][4]);
			
		}else if(atacante==2) {
			System.out.println("1."+jugadores[0][0]+": "+ "AT"+jugadores[0][1]+", DF"+jugadores[0][2]+", TR"+jugadores[0][3]+", PA"+jugadores[0][4]);
			System.out.println("2."+jugadores[2][0]+": "+ "AT"+jugadores[2][1]+", DF"+jugadores[2][2]+", TR"+jugadores[2][3]+", PA"+jugadores[2][4]);
		}else if(atacante==3) {
			System.out.println("1."+jugadores[0][0]+": "+ "AT"+jugadores[0][1]+", DF"+jugadores[0][2]+", TR"+jugadores[0][3]+", PA"+jugadores[0][4]);
			System.out.println("2."+jugadores[1][0]+": "+ "AT"+jugadores[1][1]+", DF"+jugadores[1][2]+", TR"+jugadores[1][3]+", PA"+jugadores[1][4]);
			}
	}
	
	public static void mostrarPortero(String[][]jugadores) {
		
		System.out.println("jugadores:");
		if(defensor==1) {
			System.out.println("1."+jugadores[1][0]+": "+ "AT"+jugadores[1][1]+", DF"+jugadores[1][2]+", TR"+jugadores[1][3]+", PA"+jugadores[1][4]);
			System.out.println("2."+jugadores[2][0]+": "+ "AT"+jugadores[2][1]+", DF"+jugadores[2][2]+", TR"+jugadores[2][3]+", PA"+jugadores[2][4]);
			
		}else if(defensor==2) {
			System.out.println("1."+jugadores[0][0]+": "+ "AT"+jugadores[0][1]+", DF"+jugadores[0][2]+", TR"+jugadores[0][3]+", PA"+jugadores[0][4]);
			System.out.println("2."+jugadores[2][0]+": "+ "AT"+jugadores[2][1]+", DF"+jugadores[2][2]+", TR"+jugadores[2][3]+", PA"+jugadores[2][4]);
		}else if(defensor==3) {
			System.out.println("1."+jugadores[0][0]+": "+ "AT"+jugadores[0][1]+", DF"+jugadores[0][2]+", TR"+jugadores[0][3]+", PA"+jugadores[0][4]);
			System.out.println("2."+jugadores[1][0]+": "+ "AT"+jugadores[1][1]+", DF"+jugadores[1][2]+", TR"+jugadores[1][3]+", PA"+jugadores[1][4]);
			}
	}
	
	public static void calcularJugada(int stat1, int stat2, int tipojugada) {
		
		if(tipojugada==0) {
			n=r.nextDouble()*(1.2-0.8)+0.8;
			valorataque=stat1*n;
			
			n=r.nextDouble()*(1.3-1)+1;
			valordefensa=stat2*n;
		}
		else if(tipojugada==1) {
			n=r.nextDouble()*(1.2-0.8)+0.8;
			valortiro=stat1*n;
			
			n=r.nextDouble()*(1.3-1)+1;
			valorparada=stat2*n;
		}
		
		System.out.println("");
	}
	
	public static void marcador() {
		
		System.out.println(equipo1+" "+goles1+"-"+goles2+" "+equipo2);
	}
	
	public static void tandaPenaltis() {
		
		Scanner leer=new Scanner(System.in);
		tirador=1;
		
		System.out.println("\r\nLa tanda de penaltis se disputará a 3 lanzamientos");
		System.out.println("Seleccione un portero");
		mostrarEquipo(equipo1,jugadores1);
		porteropen1=leer.nextInt();
		
		System.out.println("Seleccione un portero");
		mostrarEquipo(equipo2,jugadores2);
		porteropen2=leer.nextInt();
		
		marcador();
		
		penalti=0;
		goles1=0;
		goles2=0;
		ganar=false;
		turno=1;
		while(ganar==false) {
			
			
			//lanzamiento equipo 1
			if(turno==1) {
				System.out.println("\r\nSe dispone a lanzar "+ jugadores1[tirador-1][0]);
				System.out.print("Chutaaaaaa yyyyyy...");
				
				calcularJugada(Integer.valueOf(jugadores1[tirador-1][3]),Integer.valueOf(jugadores2[porteropen2-1][4]),1);
				
				if(valorparada>valortiro) {
					System.out.println("Paradooooon!!!");
					marcador();
					turno=2;
				}else {
					System.out.println("Golazoooooo!!!");
					goles1++;
					marcador();
				}
				
				turno=2;
			}
			
			//lanzamiento equipo 2
			else if(turno==2) {
				System.out.println("\r\nSe dispone a lanzar "+ jugadores2[tirador-1][0]);
				System.out.print("Chutaaaaaa yyyyyy...");
				
				calcularJugada(Integer.valueOf(jugadores2[tirador-1][3]),Integer.valueOf(jugadores1[porteropen1-1][4]),1);
				tirador++;
				penalti++;
				
				if(valorparada>valortiro) {
					System.out.println("Paradooooon!!!");
					marcador();
					turno=1;
				}else {
					System.out.println("Golazoooooo!!!");
					goles2++;
					marcador();
				}
				
				turno=1;
			}
			
			comprobarGanadorPenalti();
			
		}
		
	}
	
	public static void comprobarGanadorPenalti() {
		
		//comprobar ganador tanda de penaltis
		if(penalti==3) {
			if(goles1>goles2) {
				System.out.println("Ha ganado el "+equipo1);
				ganar=true;
			}
			else if(goles2>goles1) {
				System.out.println("Ha ganado el "+equipo2);
				ganar=true;
			}
			else if(goles2==goles1) {
				System.out.println("Muerte súbita");
				tirador=1;
			}
		}
		else if(penalti>3){
			if(goles1>goles2) {
				System.out.println("Ha ganado el "+equipo1);
				ganar=true;
			}
			else if(goles2>goles1) {
				System.out.println("Ha ganado el "+equipo2);
				ganar=true;
			}
			else if(goles2==goles1 && (penalti%3)==0) {
				tirador=1;
			}
			
		}
	}

	public static void mostrarEstadisticas() {
		
		int mejorjugador=0, indicei=0, indicej=0;
		
		//estadisticas equipo1
		System.out.println("\r\nEstadísticas "+equipo1);
		System.out.println("Ataques: "+contadorataques1);
		System.out.println("Tiros: "+contadortiros1);
		System.out.println("Paradas: "+contadorparadas1);	
		
		//estadisticas equipo2
		System.out.println("\r\nEstadísticas "+equipo2);
		System.out.println("Ataques: "+contadorataques2);
		System.out.println("Tiros: "+contadortiros2);
		System.out.println("Paradas: "+contadorparadas2);
		
		for (int i=0;i<jugadores.length;i++) {
			for (int j=0;j<jugadores[i].length;j++) {
				if(i==0) {
					jugadores[i][j]=jugadores1[j][0];
				}else {
					jugadores[i][j]=jugadores2[j][0];
				}
					
			}
		}
		
		for (int i=0;i<intervencionjugadores.length;i++) {
			for (int j=0;j<intervencionjugadores[i].length;j++) {
				if(intervencionjugadores[i][j]>mejorjugador) {
					mejorjugador=intervencionjugadores[i][j];
					indicei=i;
					indicej=j;
				}
			}
		}
		mvp=jugadores[indicei][indicej];
		System.out.println("\r\nJugador que más ha intervenido: "+mvp);	
		
		//reinicio de estadísticas
		for (int i=0; i<intervencionjugadores.length; i++) {
			for (int j=0;j<intervencionjugadores[i].length; j++) {
				intervencionjugadores[i][j]=0;
			}
		}
		contadorataques1=0;
		contadorataques2=0;
		contadortiros1=0;
		contadortiros2=0;
		contadorparadas1=0;
		contadorparadas2=0;
	}

	public static void modoPartido() {
		
		Scanner leer=new Scanner(System.in);
		
		
		System.out.println("Seleccione la modalidad del partido");
		System.out.println("1. Partido a goles");
		System.out.println("2. Partido a tiempo");
		tipopartido=leer.nextInt();
		
		while(tipopartido<1 || tipopartido>2) {
			System.out.println("Introduzca una opción válida");
			tipopartido=leer.nextInt();
		}
		
		if(tipopartido==1) {

			System.out.println("¿A cuántos goles quiere jugar el partido?");
			System.out.print("Nº de goles: ");
			numgoles=leer.nextInt();
			while(numgoles<1) {
				System.out.println("Por favor, introduzca un valor mayor que 0");
				System.out.print("Nº de goles: ");
				numgoles=leer.nextInt();
			}
		}else if(tipopartido==2) {
			
			System.out.println("¿A cuántos minutos quiere jugar el partido?");
			tiempopartido=leer.nextInt();
			tiempopartido*=60;
	
			tiempo.Contar();
		}
	}

	public static void comprobarTiempo() {
		
		Scanner leer=new Scanner(System.in);
		if(tipopartido==2) {
			int tiemporestante=tiempopartido-tiempo.getSegundos();
			if(tiemporestante<0) {
				tiemporestante=0;
			}
			System.out.println("Tiempo restante: "+tiemporestante);
			if(tiemporestante<=0) {
				System.out.println("¡¡¡¡PI PI PIIIIIIIIII FINAL DEL PARTIDO!!!!");
				if(goles1>goles2) {
					System.out.println("Ha ganado el "+equipo1);
					marcador();
					turno=0;
					ganar=true;
				}
				else if(goles2>goles1) {
					System.out.println("Ha ganado el "+equipo2);
					marcador();
					turno=0;
					ganar=true;
				}else {
					turno=0;
					System.out.println("Jugar tanda de penaltis");
					System.out.println("1.Si");
					System.out.println("2.No");
					int desempate=leer.nextInt();
					if(desempate==1) {
						tandaPenaltis();
						ganar=true;
					}
					else {
						System.out.println("Habéis empatado");
						ganar=true;
					}
				}
			}
		}
	}
}