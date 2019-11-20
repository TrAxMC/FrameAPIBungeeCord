package de.framedev.frameapibungeecord.main;


public interface Callback<V extends Object, T extends Throwable> {

    public void call(V result, T thrown);

}
