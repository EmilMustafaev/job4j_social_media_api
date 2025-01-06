package ru.job4j.springbootstart.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springbootstart.model.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
