package src.assets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import src.listeners.innerChangeEventListener.InnerChangeListenableClass;
import src.client.GameManager;
import src.players.Player;

/**
 * abstract class AssetGroup extends InnerChangeListenableClass implements Collection<Asset>, Offerable
 * public
 * @see java.util.Collection
 * @see Offerable
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public abstract class AssetGroup extends InnerChangeListenableClass implements Collection<Asset> {

    protected ArrayList<Asset> assetsInGroup;
    protected String nameOfGroup;

    /**
     * method  boolean isOfSoleOwnership()
     * public
     * @pre group is not empty
     * @return true if and only if  the group is owned by a single player false otherwise
     */
    public boolean isOfSoleOwnership() {
        Player owner = this.get(0).getOwner();
        for (Asset curr : this) {
            if (owner != curr.getOwner() || curr.getOwner() == GameManager.assetKeeper) {
                return false;
            }
        }
        return true;
    }

    /**
     * method AssetGroup(String nameOfGroup) - a constructor for an AssetGroup to be used by extending class
     * : public
     * @param nameOfGroup - the name to set for the group
     * @post : this.name=nameOfGroup
     *
     */
    public AssetGroup(String nameOfGroup) {
        this.assetsInGroup = new ArrayList<Asset>();
        this.nameOfGroup = nameOfGroup;
    }

    /**
     * method String getName() - get the name of the group
     * public 
     * @return the nameOfGroup - returns the name of the group
     */
    public String getName() {
        return nameOfGroup;
    }

    /**
     * method Asset get(int index) -get an Asset at an index
     * public
     * @param index - an index
     * @return the Asset in the index place
     */
    public Asset get(int index) {
        return assetsInGroup.get(index);
    }

    //collection functions
    /* (non-Javadoc)
     * @see java.util.Collection#add(java.lang.Object)
     */
    @Override
    public boolean add(Asset asset) {
        return assetsInGroup.add(asset);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends Asset> arg0) {
        return assetsInGroup.addAll(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#clear()
     */
    @Override
    public void clear() {
        assetsInGroup.clear();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object arg0) {
        return assetsInGroup.contains(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> arg0) {
        return assetsInGroup.containsAll(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return assetsInGroup.isEmpty();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#iterator()
     */
    @Override
    public Iterator<Asset> iterator() {
        return assetsInGroup.iterator();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object arg0) {
        return assetsInGroup.remove(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> arg0) {
        return assetsInGroup.removeAll(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> arg0) {
        return assetsInGroup.retainAll(arg0);
    }

    /* (non-Javadoc)
     * @see java.util.Collection#size()
     */
    @Override
    public int size() {
        return assetsInGroup.size();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#toArray()
     */
    @Override
    public Object[] toArray() {
        return assetsInGroup.toArray();
    }

    /* (non-Javadoc)
     * @see java.util.Collection#toArray(T[])
     */
    @Override
    public <T> T[] toArray(T[] arg0) {
        return assetsInGroup.toArray(arg0);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "name:" + nameOfGroup + " Contains: " + assetsInGroup.toString();
    }
}