package org.Interface;

public interface EditImageFragmentListener {
    void onBrightnessChanged(int brightness);
    void onSaturationChanged(float saturation);
    void nConstrantChanged(float constrant);
    void onEditStarted();
    void onEditCompleted();
}
