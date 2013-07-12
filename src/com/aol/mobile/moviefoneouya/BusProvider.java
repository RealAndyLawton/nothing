package com.aol.mobile.moviefoneouya;

import com.squareup.otto.Bus;

public final class BusProvider {

private static final Bus BUS = new Bus();
	
	public static Bus getBusInstance() {
	    return BUS;
	 }
	
}
