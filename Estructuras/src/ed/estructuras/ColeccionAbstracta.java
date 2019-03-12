package ed.estructuras;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public abstract class ColeccionAbstracta<E> implements Collection<E> {

    /* Numero de elementos que tiene la estructura */
    protected int tam = 0;

    @Override
    public boolean addAll(Collection<? extends E> c) throws  NullPointerException,
                                                             IllegalArgumentException,
                                                             IllegalStateException {
        boolean coleccionModificada = false;
        if(c == null) {
            throw new NullPointerException();
        }

        if(c.equals(this)) {
            throw new IllegalArgumentException("Intentas agregar una copia de esta coleccion");
        }

        Iterator<?> transeunte = c.iterator();

        while(transeunte.hasNext()) {
            E porAgregar = (E) transeunte.next();
            this.add(porAgregar);
            coleccionModificada = true;
        }
        return coleccionModificada;
    }

    @Override
    /* Elimina todos los elementos de la estructura */
    public void clear() {
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            transeunte.next();
            transeunte.remove();
        }
    }

    @Override
    public boolean contains(Object o) throws NullPointerException {
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            Object elemento = transeunte.next();
            if(elemento == null && o == null) {
                return true;
            }

            if(elemento == null) {
                continue;
            }

            if(elemento.equals(o)) return true;
        }
        return false;
    }

    @Override
    /* Equivalente a ¿c es subconjunto de this? */
    public boolean containsAll(Collection<?> c) throws NullPointerException {
        if(c == null) {
            throw new NullPointerException();
        }

        Iterator<?> transeunte = c.iterator();
        while(transeunte.hasNext()) {
            if(!this.contains(transeunte.next())) return false;
        }
        return true;
    }

    public boolean equals(Object o) {
        if(o == null) return false;
        return this.toString() == o.toString();
    }

    @Override
    /* El hashcode de nuestros objetos es suma del hash de cada uno de sus objetos */
    public int hashCode() {
        int identificador = 0;
        for(E elemento: this) {
            identificador += elemento.hashCode();
        }
        return identificador;
    }

    @Override //Lo que sigue se puede a hacer con  un for each
    /* Elimina (si existe) sólo la primera ocurrencia del elemento en la coleccion */
    public boolean remove(Object o) throws NullPointerException {
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            Object elemento = transeunte.next();
            if(elemento == null && o == null) {
                transeunte.remove();
                this.tam--;
            }
            if(elemento == null) {
                continue;
            }

            if(elemento.equals(o)) {
                transeunte.remove();
                this.tam--;
                return true;
            }
        }
        return false;
    }

    @Override //Lo que sigue se puede implementar con un for each
    /* Diferencia de conjuntos A\B */
    public boolean removeAll(Collection <?> c) throws NullPointerException {
        boolean coleccionModificada = false;
        if(c == null) {
            throw new NullPointerException();
        }

        Iterator<?> checador = c.iterator();

        while(checador.hasNext()) {
            Object elemento = checador.next();
            if(this.contains(elemento)) {
                this.remove(elemento);
                coleccionModificada = true;
            }
        }
        return coleccionModificada;
    }

    @Override
    /* Interseccion de conjuntos */
    //revisar si es la misma
    public boolean retainAll(Collection<?> c) throws NullPointerException {
        boolean coleccionModificada = false;

        if(c == null) {
            throw new NullPointerException();
        }


        Iterator<E> checador = this.iterator();

        while(checador.hasNext()) {
            Object elemento = checador.next();
            if(!(c.contains(elemento))) {
                this.remove(elemento);
                coleccionModificada = true;
            }
        }
        return coleccionModificada;
    }

    @Override
    public int size() {
        return tam;
    }

    @Override //lo que sigue se puede hacer con un for each
    /* Los elementos en este arreglo no necesariamente están ordenados */
    public Object[] toArray() {
        Object[] representacion = new Object[this.size()];
        int contador = 0;
        Iterator<E> transeunte = this.iterator();

        while(transeunte.hasNext()) {
            representacion[contador] = transeunte.next();
            contador++;
        }
        return representacion;
    }

    @Override
    /* Los elementos en el arreglo no necesariamente están ordenados */
    public <T> T[] toArray(T[] a) throws ArrayStoreException, NullPointerException {
        if(a == null) {
            throw new NullPointerException();
        }

        int nvoTam;
        if(a.length >= this.size()) {
            nvoTam = a.length;
        }
        else {
            nvoTam = this.size();
        }

        T[]  contenedor = Arrays.copyOf(a,nvoTam);

        int contador = 0;
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            T elemento = (T) transeunte.next();
            contenedor[contador] = elemento;
            contador++;
        }
        return contenedor;
    }

    @Override
    public String toString() {
        String representacion = "";
        for(E elemento : this) {
            if(elemento == null) {
                representacion += "null";
            }
            representacion += elemento.toString();
        }
        return representacion;
    }

    //metodos por implementar dependiendo de cada clase
    public abstract boolean add(E e);

    public abstract Iterator<E> iterator();

    public abstract boolean isEmpty();
}
