package com.example.josip.jstest.activities;

import android.util.SparseArray;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Josip on 22/09/2014!
 */
public abstract class MarkerListAdapter<T> {

    public final int DEFAULT_MARKER_PADDING = 144;

    private GoogleMap map;
    private List<T> items;

    private int markerPadding;

    private SparseArray<Marker> markers;
    private int firstVisibleItemPosition, lastVisibleItemPosition;

    public MarkerListAdapter(GoogleMap googleMap, int markerPadding, List<T> items) {
        this(googleMap, items);

        if (markerPadding < 0) {
            throw new IllegalArgumentException("markerPadding should be non-negative");
        }
        this.markerPadding = markerPadding;
    }

    public MarkerListAdapter(GoogleMap googleMap, List<T> items) {
        if (googleMap == null) {
            throw new NullPointerException("googleMap should not be null");
        }
        if (items == null) {
            throw new NullPointerException("items should not be null");
        }

        this.map = googleMap;
        this.items = items;

        this.markers = new SparseArray<Marker>(items.size());
        this.markerPadding = DEFAULT_MARKER_PADDING;
    }

    public abstract MarkerOptions getMarkerOptionsForItem(T item, int itemPosition);

    public void scrollTo(int firstVisibleItemPosition, int visibleItemCount) {
        if (firstVisibleItemPosition < 0) {
            throw new IllegalArgumentException("firstVisibleItemPosition should be non-negative");
        }
        if (visibleItemCount < 0) {
            throw new IllegalArgumentException("visibleItemCount should be non-negative");
        }

        this.firstVisibleItemPosition = firstVisibleItemPosition;

        int lastVisibleItemPosition = visibleItemCount + firstVisibleItemPosition - 1;
        this.lastVisibleItemPosition = lastVisibleItemPosition < items.size() ? lastVisibleItemPosition : items.size();

        showHideMarkers();
    }

    public void clickOn(int itemPosition) {
        if (itemPosition >= firstVisibleItemPosition && itemPosition <= lastVisibleItemPosition) {
            Marker marker = markers.get(itemPosition);

            marker.showInfoWindow();

        }
    }

    public void notifyDataSetChanged() {
        markers = new SparseArray<Marker>(items.size());
        showHideMarkers();
    }

    public void add(T item) {
        items.add(item);
    }

    public void addAll(Collection<? extends T> items) {
        this.items.addAll(items);
    }

    public void addAll(T... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        markers.clear();
    }

    public int getCount() {
        return items.size();
    }

    private void showHideMarkers() {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        boolean markersIncludedInBound = false;

        //iterate through all items, showing, hiding and constructing markers as necessary
        for (int i = 0; i < items.size(); ++i) {
            Marker marker = markers.get(i);

            if (i < firstVisibleItemPosition || i > lastVisibleItemPosition) {
                //marker is no longer visible, should be hidden if it exists
                if (marker != null) {
                    marker.setVisible(false);
                }
            } else {
                //marker is visible, should be constructed if it doesn't exist
                if (marker != null) {
                    marker.setVisible(true);
                } else {
                    marker = map.addMarker(getMarkerOptionsForItem(items.get(i), i));
                    markers.put(i, marker);
                }
                boundsBuilder.include(marker.getPosition());
                markersIncludedInBound = true;
            }
        }

        //center map around visible markers:
        if (markersIncludedInBound) {
            LatLngBounds bounds = boundsBuilder.build();
            centerMapCameraOnBounds(bounds);
        }
    }

    private void centerMapCameraOnBounds(LatLngBounds bounds) {
        final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, markerPadding);
        try{
            map.animateCamera(cameraUpdate);
        } catch (IllegalStateException mapLayoutMissingException) {
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    map.moveCamera(cameraUpdate);
                }
            });
        }
    }
}
