package org.example.springv3.user;

import lombok.RequiredArgsConstructor;
import org.example.springv3.core.error.ex.Exception401;
import org.example.springv3.core.error.ex.Exception400;
import org.example.springv3.reply.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;


    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
                .orElseThrow(()-> new Exception401("인증되지 않았습니다"));

            return user;

    }

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {

        Optional<User> userOP = userRepository.findByUsername(joinDTO.getUsername());

        if(userOP.isPresent()) {
            throw new Exception400("이미 존재하는 유저입니다.");
        }

        userRepository.save(joinDTO.toEntity());

    }

    public boolean 유저네임중복되었니(String username) {
        System.out.println("1"+username);
        Optional<User> userOP = userRepository.findByUsername(username);
        System.out.println("2"+userOP.isPresent());
        if(userOP.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
