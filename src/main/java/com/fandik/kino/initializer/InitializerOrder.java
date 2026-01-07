package com.fandik.kino.initializer;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializerOrder implements SmartInitializingSingleton {

  @Autowired
  private UserInitializer userInitializer;
  @Autowired
  private MovieInitializer movieInitializer;
  @Autowired
  private PerformanceInitializer performanceInitializer;
  @Autowired
  private ReservationInitializer reservationInitializer;

  @Override
  public void afterSingletonsInstantiated() {
    userInitializer.run();
    movieInitializer.run();
    performanceInitializer.run();
    reservationInitializer.run();
  }
}
