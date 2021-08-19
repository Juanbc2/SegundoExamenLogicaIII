import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class Ventana  extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JButton insertarButton;
    private JLabel Label;
    private JButton salirButton;
    private JButton enhebrarPosordenButton;
    private JTextPane resultados;
    private LDLCNC arbol = new LDLCNC();

    public Ventana(){
        super("Examen II Lógica III - Juan Guillermo Buitrago Calle");
        setContentPane(panel1);
        resultados.setText("Árbol en posorden con sus factores de balance.\n");
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    arbol.insertarDato(Integer.parseInt(textField1.getText()));
                    imprimirSinEnhebrar();
                }catch(NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"Por favor, ingrese sólo números en el campo de texto.");
                }


            }
        });
        enhebrarPosordenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol.enhebrarPosorden();
                imprimirEnhebrado();
                insertarButton.setEnabled(false);
                enhebrarPosordenButton.setEnabled(false);
                textField1.setEnabled(false);
                Label.setText("");
            }
        });
    }

    public void imprimirEnhebrado(){
        ArrayList<nodoDoble> lista = arbol.getListOrden();
        int i = 0;
        resultados.setText("Árbol en posorden con sus factores de balance y ligas.\n");
        while(i<lista.size()) {
            resultados.setText("Nodo "+resultados.getText()+"("+lista.get(i).retornaDato()+") | Factor de Balance: " + lista.get(i).getFactor()+
                    " | Li: ("+lista.get(i).retornaLi().retornaDato()+") | Ld: ("+lista.get(i).retornaLd().retornaDato()+").\n");
            i++;
        }
    }

    public void imprimirSinEnhebrar(){
        arbol.getListOrden().clear();
        arbol.posorden(arbol.getRaiz());
        ArrayList<nodoDoble> lista = arbol.getListOrden();
        int i = 0;
        resultados.setText("Árbol en posorden con sus factores de balance.\n");
        while(i<lista.size()) {
            resultados.setText(resultados.getText()+"Nodo "+"("+lista.get(i).retornaDato()+") Factor de Balance: " + lista.get(i).getFactor()+".\n");
            i++;
        }
        System.out.println("-----------");
    }

}



class LDLCNC {

    private nodoDoble primero, ultimo,raiz,primeroPosorden;
    private ArrayList<nodoDoble> listOrden = new ArrayList<>();
    private int length = 0;
    public ArrayList<nodoDoble> getListOrden() {
        return listOrden;
    }

    public int getLength() {
        return length;
    }

    public LDLCNC() {
        this.primero = new nodoDoble(null);
        this.primero.asignaLi(primero);
        this.primero.asignaLd(primero);
    }

    public nodoDoble getRaiz() {
        return raiz;
    }

    public void setRaiz(nodoDoble raiz) {
        this.raiz = raiz;
    }


    public void posorden(nodoDoble r) {
        if (r != null) {
            posorden(r.retornaLi());
            posorden(r.retornaLd());
            System.out.println(r.retornaDato());
            listOrden.add(r);
        }
    }

    public void enhebrarPosorden(){
        for (nodoDoble n: listOrden
        ) {
            if(n.retornaLi() == null){
                int i = listOrden.indexOf(n);
                if(i>0){
                    n.asignaLi(listOrden.get(i-1));
                }else{
                    n.asignaLi(getRaiz());
                }
            }
            if(n.retornaLd() == null){
                int i = listOrden.indexOf(n);
                if(i!=listOrden.size()-1){
                    n.asignaLd(listOrden.get(i+1));
                }else{
                    n.asignaLd(getRaiz());
                }
            }
        }
    }

    public void insertarDato(int d){
        this.length++;
        nodoDoble x = new nodoDoble(d);
        if (getRaiz() == null) {
            setRaiz(x);
            return;
        }
        nodoDoble p = getRaiz();
        nodoDoble q = null;
        nodoDoble pivote = getRaiz();
        nodoDoble pp = null;
        while (p != null) {
            if ((int)p.retornaDato() == d) {
                return;
            }
            if(p.getFactor() != 0){
                pivote = p;
                pp = q;
            }
            q = p;
            if ((int) p.retornaDato() >  d) {
                p = p.retornaLi();
            } else {
                p = p.retornaLd();
            }
        }
        if ((int) q.retornaDato() > d) {
            q.asignaLi(x);
        } else {
            q.asignaLd(x);
        }
        int aux = pivote.getFactor();
        if((int)pivote.retornaDato() > d){
            pivote.setFactor(aux+1);
            q = pivote.retornaLi();
        }else{
            pivote.setFactor(aux-1);
            q = pivote.retornaLd();
        }
        p = q;
        while(p != x){
            if((int)p.retornaDato() > d){
                p.setFactor(+1);
                p = p.retornaLi();
            }else{
                p.setFactor(-1);
                p = p.retornaLd();
            }
        }
    }
}


class nodoDoble
{
    private Object dato;
    private nodoDoble ld,li;
    int factor;


    public nodoDoble(Object d)
    {
        this.dato = d;
        li = null;
        ld = null;
    }

    public void asignaDato(Object d)
    {
        dato = d;
    }

    public void asignaLi(nodoDoble x)
    {
        li = x;
    }
    public void asignaLd(nodoDoble x)
    {
        ld = x;
    }
    public Object retornaDato()
    {
        return this.dato;
    }
    public nodoDoble retornaLi()
    {
        return this.li;
    }
    public nodoDoble retornaLd()
    {
        return this.ld;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
}


