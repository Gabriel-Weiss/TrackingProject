package md.ex.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.ex.demo.model.User;
import md.ex.demo.repository.UserRepository;
import md.ex.demo.twilio.TwilioService;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final UserRepository userRepository;
    private final TwilioService twilioService;

    public Mono<User> getByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public Flux<User> getUsers() {
        return userRepository.findBy().onErrorStop();
    }

    public Mono<User> addUser(User user) {
        return userRepository.save(user);
    }

//    public void changeStatus(String userId) {
//        User user = getByUserId(userId);
//        user.setUserStatus(!user.getUserStatus());
//
//        twilioService.notifyInfected(user.getPhone());
//        log.info("notifyInfected(): called. Send Message with twilio service to " + user.getPhone());
//
//        List<String> list = user
//                .getDates()
//                .stream()
//                .map(Date::getSavedCodes)
//                .flatMap(Collection::stream)
//                .distinct().collect(Collectors.toList());
//        list.forEach(s -> {
//            String userPhone = null;
//            try {
//                userPhone = getUserByUserId(s).getUserPhone();
//            } catch (NoSuchElementException e) {
//                log.error("User with id" + s + " not present");
//            }
//            twilioService.notifyQuarantin(userPhone);
//            log.info("notifyQuarantin(): called. Send Message with twilio service to " + userPhone);
//        });
//
//        userRepository.save(user);
//    }
}