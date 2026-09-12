package com.music.vibe;

import android.content.Context;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> colorLiveData = new MutableLiveData<>();
    private Context context;  // Necesario para mostrar Toast

    public void setContext(Context ctx) {
        this.context = ctx;
    }

    public void setColor(int color) {
    if (context != null) {
        
    }
    colorLiveData.setValue(color);
}

    public LiveData<Integer> getColor() {
        return colorLiveData;
    }
}