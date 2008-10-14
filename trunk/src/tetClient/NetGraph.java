package tetClient;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import edu.umd.cs.piccolox.swt.SWTGraphics2D;
import java.awt.Polygon;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import parser.Parser;
import tetPns.Element;
import tetPns.PetriNet;
import tetPns.Place;
import tetPns.Transition;
import tetPns.Arc;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.util.PNodeLocator;

/**
 * Classe che disegna il grafico della rete di Petri
 * @author michele
 *
 */

public class NetGraph extends PCanvas {

	
	private static final long serialVersionUID = 1L;
	
	// suddivideo il disegno in layer
	PLayer nodeLayer = getLayer();
	PLayer edgeLayer = new PLayer();
	PLayer labelLayer = new PLayer();
	PLayer arrowLayer = new PLayer();
	
	//dimensioni della transizione
	int largTrans = 30;
	int altTrans = 10;
	
	//raggio del place
	int ragPlace = 20;
	
	public NetGraph(int width, int height, PetriNet pn) {
		setPreferredSize(new Dimension(width, height));
		this.removeInputEventListener(this.getPanEventHandler());
		 
		this.removeInputEventListener(this.getZoomEventHandler());


		//aggiungo i vari layer alla finestra
		getRoot().addChild(edgeLayer);
		getCamera().addLayer(0, edgeLayer);
		getRoot().addChild(labelLayer);
		getCamera().addLayer(0,labelLayer);
		getRoot().addChild(arrowLayer);
		getCamera().addLayer(0,arrowLayer);

		PPath node = null;
		int totNode = 0;
		
		// disegno tutti i places
		for(Place place : pn.getPlaces()) {
			node = PPath.createEllipse(place.getXCoord(), place.getYCoord(), ragPlace, ragPlace);
			node.addAttribute("edges", new ArrayList());
			node.addAttribute("id", place.getId());
			node.addAttribute("type", "place");
			PText text = new PText(Integer.toString(place.getToken()));
			node.addChild(text);
			
			PNodeLocator pbl = new PNodeLocator(node);
			 		text.setOffset(
			 			pbl.locateX() - (0.5 * text.getBounds().getWidth()),
			 			pbl.locateY() - (0.5 * text.getBounds().getHeight()));
			 		text.setPickable(false);
			
			text = new PText(place.getId());
			labelLayer.addChild(text);
			
	 		text.setOffset(place.getLabelX()+pbl.locateX(), place.getLabelY()+pbl.locateY());
	 		text.setPickable(false);	
			
			nodeLayer.addChild(node);
			totNode++;
		}

		// disegno tutte le transizioni
		for(Transition transition : pn.getTransitions()) {

			node = PPath.createRectangle(transition.getXCoord(), transition.getYCoord(), largTrans, altTrans);

			node.addAttribute("edges", new ArrayList());
			node.addAttribute("id", transition.getId());
			node.addAttribute("type", "transition");
			nodeLayer.addChild(node);
			
			
			PNodeLocator pbl = new PNodeLocator(node);

			
			PText text = new PText(transition.getId());
			labelLayer.addChild(text);
			
	 		text.setOffset(transition.getLabelX()+pbl.locateX(), transition.getLabelY()+pbl.locateY());
	 		text.setPickable(false);	
			
			totNode++;
		}
		
		int i;
		PNode nodeTest = null;
		
		//disegno tutti gli archi:
		for(Arc arc : pn.getArcs()) {
			//arc.getInfo();
			String in = arc.getSourceElement().getId();
			String out = arc.getTargetElement().getId();
			PNode node1 = null, node2 = null;
			for(i=0;i<totNode;i++) {
				nodeTest = nodeLayer.getChild(i);
				if(nodeTest.getAttribute("id")==in) {
					node1 = nodeLayer.getChild(i);
				}
				if(nodeTest.getAttribute("id")==out) {
					node2 = nodeLayer.getChild(i); 
				}
			}
			
			if(node1 != null && node2 != null && node1!=node2) {
				PPath edge = new PPath();
				((ArrayList)node1.getAttribute("edges")).add(edge);
				((ArrayList)node2.getAttribute("edges")).add(edge);
				edge.addAttribute("nodes", new ArrayList());
				((ArrayList)edge.getAttribute("nodes")).add(node1);
				((ArrayList)edge.getAttribute("nodes")).add(node2);
				
				
				edgeLayer.addChild(edge);
				updateEdge(edge);			
			}
		}
			

	}
	
	
	
	
	public void updateEdge(PPath edge) {

		PNode node1 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(0);
		PNode node2 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(1);
		Point2D start = node1.getFullBoundsReference().getCenter2D();
		Point2D end = node2.getFullBoundsReference().getCenter2D();
		edge.reset();
		edge.moveTo((float)start.getX(), (float)start.getY());
		edge.lineTo((float)end.getX(), (float)end.getY());
		float x1 = (float)end.getX();
		float y1 = (float)end.getY();
		float x2 = (float)start.getX();
		float y2 = (float)start.getY();
		float m = (y2-y1)/(x2-x1);
		float m2 = m+1;
		float m3 = m-1;
		this.drawArrow(arrowLayer, (int)x2, (int)y2, (int)x1, (int)y1, 2, (node2.getAttribute("type")).toString());
		PPath node = new PPath();
		//node.moveTo(x-2,y);
		//node.lineTo(x+2, y+2);
		//System.out.println("\nm="+angolo);
		nodeLayer.addChild(node);
	}
	


	/**
	 * Funzione complicatissima per il disegno delle frecce. DA NOTARE LO SFORZO
	 * @param g2d il layer delle frecce
	 * @param xCenter x inizio arco
	 * @param yCenter y inizio arco
	 * @param x x fine arco
	 * @param y y fine artco
	 * @param stroke 
	 * @param type
	 */
	 public  void drawArrow(PLayer g2d, int xCenter, int yCenter, int x, int y, float stroke, String type) {
		  float mx[] = new float[5], my[] = new float[5];
	      double aDir=Math.atan2(xCenter-x,yCenter-y);
	      PPath p = null;
	      p = PPath.createLine(x,y,xCenter,yCenter);
	      int i1=12+(int)(stroke*2);
	      int i2=6+(int)stroke;					

	      
	      if(type.equals("place")) {
	      	  x+=ragPlace/2*Math.sin(aDir);
	    	  y+=ragPlace/2*Math.cos(aDir);
	      }	else {

	    	  float angComp = (float) Math.atan2((float)largTrans,(float)altTrans);
	    	  //System.out.println("\nAngoComp : " + angComp);
	    	 
	    	  
	    	  if(aDir<=angComp && aDir>=(-angComp)){ //Lato sotto
	    		  y+=altTrans/2;
	    		  x+=(altTrans/2)*Math.tan(aDir);
	    	  } else if(aDir <= Math.PI-angComp && aDir >=angComp) { // lato dx
	    		  x+=largTrans/2;
	    		  y+=(largTrans/2)*Math.tan(Math.PI/2-aDir);
	    	  } else if(aDir >= angComp-Math.PI && aDir <= -angComp) { //lato sx
	    		  x-=largTrans/2;
	    		  y-=(largTrans/2)*Math.tan(Math.PI/2-aDir);

	    	  } else { // lato sopra
	    		y-=altTrans/2;
	    		x-=(altTrans/2)*Math.tan(aDir);
	    	  }
	      }
	      
	      
	      mx[0]=x;
	      my[0]=y;
	      mx[1]=x+xCor(i1,aDir+.5);
	      my[1]=y+yCor(i1,aDir+.5);
	      mx[2]=x+xCor(i2,aDir);
	      my[2]=y+yCor(i2,aDir);
	      
	      mx[3]=x+xCor(i1,aDir-.5);
	      my[3]=y+yCor(i1,aDir-.5);
	      mx[4]=x;
	      my[4]=y;
	      p = PPath.createPolyline(mx,my);
	      g2d.addChild(p);
	   }
	   private static int yCor(int len, double dir) {return (int)(len * Math.cos(dir));}
	   private static int xCor(int len, double dir) {return (int)(len * Math.sin(dir));}
	
	
}