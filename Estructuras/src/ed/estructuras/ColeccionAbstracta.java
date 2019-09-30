package ed.estructuras;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Clase que implementa la interfaz Collection.<p>
 * Una coleccion cuenta con un numero definido de elementos.
 */

/*
*** Características de *esta* la colección:
* - Permite elementos nulos
* - Permite elementos repetidos
* - Los elementos no necesariamente cumplen con un criterio de orden
*** Detalles de la Implementación
* - Para métodos que modifican a la colección. El tamaño de la colección sólo es modificada por el iterador y el método add
*/
public abstract class ColeccionAbstracta<E> implements Collection<E> {

    /* Cantidad de elementos que tiene la estructura */
    protected int tam = 0;

    /**
     * Agrega todos los elementos de la colección c a la colección que manda a llamar el método.
     * @param c colección con elementos por agregar. La colección se agrega si es
     * distinta de <code>null<code> y distinta de sí misma.
     * @return <tt>true</tt> si se agregó al menos un elemento a la
     * colección, <tt>false</tt> en otro caso.
     */
    @Override
    public boolean addAll(Collection<? extends E> c) throws  NullPointerException,
                                                             IllegalArgumentException {
        boolean coleccionModificada = false;
        if(c == null) {
            throw new NullPointerException();
        }

        if(c == this) {
            throw new IllegalArgumentException("Intentas agregar esta colección a sí misma");
        }

        // Se puede hacer que elemento sea de tipo ( instancia(?) ) E porque ? extiende a E
        for(E elemento : c) {
            this.add(elemento);
            //consideramos que el método add aumenta el tamaño de la estructura
            //this.tam++;
            coleccionModificada = true;

        }

        return coleccionModificada;
    }

    /**
     * Elimina todos los elementos de la estructura
     */
    @Override
    public void clear() {
        // No se utilizó for-each porque no es seguro con  remove() adentro.
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            transeunte.next();
            transeunte.remove();
            //Consideramos que remove disminuye el tamaño
            //this.tam--
        }
    }

    /**
     * Verifica si la coleccion contiene al objeto o.
     * @param o objeto a buscar en la estructura
     * @return <tt>true</tt> si el objeto aparece al menos una vez en
     * la colección
     */
    @Override
    public boolean contains(Object o) throws NullPointerException {
        for(E elemento : this) {
            if(elemento == null && o == null) return true;
            if(elemento == null) continue;
            if(elemento.equals(o)) return true;
        }

        return false;
    }

    /**
     * Verifica si la colección contiene todos los elementos de la
     * coleccion c.
     * @param c colección de la cual se va a verificar la
     * contención. Solo se verifica la contención si c es distinto de
     * <code>null<code>
     * @return <tt>true</tt> si la colección contiene todos los
     * elementos de la colección c <tt>false</tt> en otro caso.
     */
    @Override
    /* Equivalente a ¿c es subconjunto de this? */
    public boolean containsAll(Collection<?> c) throws NullPointerException {
        if(c == null) {
            throw new NullPointerException();
        }
        //duda ¿que pasa si es elemento = null ? se rompe con la asignacion a object? en caso de que sí ¿qué pasa cuando entra a contains?
        //en caso de que elemento sea  null y se rompa con la asignación a object hay que dejar la implementacion con el iterador
        for(Object elemento : c) {
            if(!this.contains(elemento)) return false;
        }
        return true;
    }

    /**
     * Nos dice si dos objetos son iguales.
     * El criterio de igualdad se cumple si dos estructuras tienen a los mismos elementos y están en la misma posición.
     * @param o el objeto con el cual se va a comparar
     * @return <tt>true</tt> si los objetos son iguales, <tt>false</tt> si no.
     */
    @Override
    //revisar, todavia podría haber casos que no estoy considerando
    public boolean equals(Object o) {
        if(o == null) return false;

        if(!(o instanceof Collection<?>)) return false;

        Collection<?> objetoEquivalente = (Collection<?>) o;

        if(this.size() != objetoEquivalente.size()) return false;

        Iterator<E> checador = this.iterator();
        Iterator<?> checador2 = objetoEquivalente.iterator();

        while(checador.hasNext()) {
            E elemento = checador.next();
            Object elemento2 = checador2.next();
            if(elemento == null) {
                if(!(elemento == null)) return false;
                continue;
            }

            if(!elemento.equals(elemento)) return false;
        }
        return true;
    }

    /**
     * Nos da un código identificador único para cada objeto.
     * @return identificador el código con el cual el objeto se identifica.
     */
    @Override
    /* El hashcode de nuestra colección es suma del hash de cada uno de los objetos que contiene */
    //Duda ¿qué pasa si  dos estructuras contienen solo elementos nulos?
    //En caso de que se utilice el hash para verificar igualdad, tendríamos que dos objetos que contienen solo elementos nulos
    //su hashcode es igual sin importar cuantos null tengan
    public int hashCode() {
        int identificador = 0;
        for(E elemento: this) {
            if(elemento == null) continue;
            identificador += elemento.hashCode();
        }
        return identificador;
    }

    /**
     * Nos dice si la colección no contiene elementos
     * @return <tt>true</tt> si la colección es vacía, <tt>false</tt> en otro caso.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Elimina en la colección (si existe) sólo la primera instancia del elemento especificado
     * @param o el objeto a eliminar (si existe) en la colección.
     * @return <tt>true</tt> si se elimina una instancia de la colección, <tt>false</tt> si no.
     */
    @Override
    //No se puede usar for-each pues se utiliza .remove()
    public boolean remove(Object o) throws NullPointerException {
        Iterator<E> transeunte = this.iterator();
        while(transeunte.hasNext()) {
            Object elemento = transeunte.next();
            if(elemento == null && o == null) {
                transeunte.remove();
                return true;
                //el control del tamaño de la colección está en el iterador
                //this.tam--;
            }

            if(elemento == null) {
                continue;
            }

            if(elemento.equals(o)) {
                transeunte.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina todas las ocurrencias de la colección, que estén contenidas en la colección c
     * @param c la colección que indica qué elementos se eliminarán de la colección que manda llamar el método
     * @return <tt>true</tt> si al menos un elemento de la colección fue eliminado, <tt>false</tt> en otro caso.
     */
    @Override
    /* Diferencia de conjuntos A\B */
    public boolean removeAll(Collection <?> c) throws NullPointerException {
        boolean coleccionModificada = false;
        if(c == null) {
            throw new NullPointerException();
        }

        Iterator<E> checador = this.iterator();

        while(checador.hasNext()) {
            if(c.contains(checador.next())) {
                checador.remove();
                coleccionModificada = true;
            }
        }

        return coleccionModificada;
    }

    /**
     * Al finalizar este método, la colección sólo conserva los
     * elementos que tiene en común con la colección c
     * @param c coleccion tal que será referencia para decidir qué
     * elementos permanecen en la colección
     * @return <tt>true</tt> si se eliminó al menos un elemento de la
     * colección que manda a llamar al método, <tt>false</tt> en otro
     * caso.
     */
    @Override
    /* Interseccion de conjuntos */
    /* No cambiar con estructura de for-each porque es inseguro usar .remove() adentro*/
    public boolean retainAll(Collection<?> c) throws NullPointerException {
        boolean coleccionModificada = false;

        if(c == null) {
            throw new NullPointerException();
        }
        if(c == this) {
            return coleccionModificada;
        }

        Iterator<E> checador = this.iterator();

        while(checador.hasNext()) {
            if(!(c.contains(checador.next()))) {
                checador.remove();
                coleccionModificada = true;
            }
        }
        return coleccionModificada;
    }

    /**
     * Indica la cantidad de elementos de nuestra colección
     * @return tam cantidad de elementos que contiene la colección
     */
    @Override
    public int size() {
        return tam;
    }

    /**
     * Entrega un arreglo que contiene todos los elementos que existen
     * actualmente en la colección y en el mismo orden en que están
     * contenidos en ésta.
     * @return representación arreglo con los elementos actuales de la
     * colección.
     */
    @Override
    /* Los elementos en este arreglo no necesariamente cumplen con un criterio de orden */
    public Object[] toArray() {
        Object[] representacion = new Object[this.size()];
        int contador = 0;

        for(Object elemento : this) {
            representacion[contador] = elemento;
            contador++;
        }

        return representacion;
    }

    /**
     * Entrega un arreglo de tipo T que contiene todos los elementos
     * que existen actualmente en la colección y en el mismo orden en
     * que están contenidos en la coleccion
     * @return contenedor arreglo con los elementos actuales de la
     * colección
     */
    @Override
    /* Los elementos en el arreglo no necesariamente cumplen con un criterio de orden */
    public <T> T[] toArray(T[] a) throws NullPointerException {
        if(a == null) {
            throw new NullPointerException("El arreglo no puede ser nulo");
        }

        if(this.isEmpty()) return a;

        //Caso en que el tamaño del arreglo es suficiente para la
        //colección
        if(a.length >= this.size()) {
            Iterator<E> copiador = this.iterator();
            for(int i = 0; i < a.length; i++) {

                if(copiador.hasNext()) {
                    T aux = (T) copiador.next();
                    a[i] = aux;
                    continue;
                }
                a[i] = null;
            }
            return a;
        }

        int nvoTam = this.size();

        T[] contenedor = Arrays.copyOf(a,nvoTam);
        int contador = 0;
        for(Object elemento: this) {
            T aux = (T) elemento;
            contenedor[contador] = aux;
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

    public abstract boolean add(E e);

    public abstract Iterator<E> iterator();
}
