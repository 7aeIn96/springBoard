package org.project.board.controllers.members;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.board.models.member.JoinValidator;
import org.project.board.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member") // 공통 URL
public class MemberController {
    private final MemberSaveService saveService;
    private final JoinValidator joinValidator;

    @GetMapping("/join")
    public String join(@ModelAttribute JoinForm joinForm, Model model) {
        commonProcess(model);
        return "member/join";
    }
    @GetMapping("/login")
    public String login() {

        return "member/login";
    }

    @PostMapping("/join")  // 가입 처리 ( 검증 필요 )
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {
        commonProcess(model);
        joinValidator.validate(joinForm, errors);
        if (errors.hasErrors()) { // 검증 후 에러가 있다면 다시 join으로
            return "member/join";
        }
        saveService.save(joinForm); // 에러 없으면 saveService로

        return "redirect:/member/login";
    }
    private void commonProcess(Model model){
        model.addAttribute("pageTitle", "회원가입");
    }
}
