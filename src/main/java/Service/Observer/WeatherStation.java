package Service.Observer;

import java.util.List;

public class WeatherStation implements WeatherSubject {

    private List<WeatherObserver> observers;
    private String data;

    public void setData(String data){
        this.data = data;
        notifyObservers();
    }

    @Override
    public void addObserver(WeatherObserver observer) {
    observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
    observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
    for (WeatherObserver observer : observers) {
        observer.updateWeather();
    }
    }
}
