package pl.waw.mrn.kudu.sample.app.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.waw.mrn.kudu.sample.app.dao.model.UserInfo;
import pl.waw.mrn.kudu.sample.app.dao.repository.UserInfoRepository;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserInfoController {

    private final UserInfoRepository userInfoRepository;

    @GetMapping
    public List<UserInfo> getAllUserInfo() {
        return userInfoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("id") String id) {
        return ResponseEntity.of(userInfoRepository.findById(id));
    }

    @PutMapping("/{id}")
    public void updateOrCreate(@RequestBody UserInfo userInfo, @PathVariable("id") String id) {
        userInfo.setId(id);
        userInfoRepository.save(userInfo);
    }
}
