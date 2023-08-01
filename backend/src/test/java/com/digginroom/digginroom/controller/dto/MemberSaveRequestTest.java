package com.digginroom.digginroom.controller.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberSaveRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @ParameterizedTest
    @ValueSource(strings = {"power", "12345", "power123"})
    public void 유저_아이디_검증_성공_테스트(final String username) {
        MemberSaveRequest request = new MemberSaveRequest(
                username,
                "Password123!"
        );

        Set<ConstraintViolation<MemberSaveRequest>> violations = validator.validate(request);

        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "1234", "abcd", "abc한글", "", "abcde!", "aaaaaaaaaaaaaaaaaaaa1"})
    public void 유저_아이디_검증_실패_테스트(final String username) {
        MemberSaveRequest request = new MemberSaveRequest(
                username,
                "Password123!"
        );

        Set<ConstraintViolation<MemberSaveRequest>> violations = validator.validate(request);
        Assertions.assertThat(violations.size()).isPositive();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh1@", "1abcdefgh@", "@abcdefgh1"})
    public void 유저_비밀번호_검증_성공_테스트(final String password) {
        MemberSaveRequest request = new MemberSaveRequest(
                "power",
                password
        );

        Set<ConstraintViolation<MemberSaveRequest>> violations = validator.validate(request);

        assertEquals(0, violations.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "abcg1@", "abcd12345", "abc한글!!332", "", "abcde!@#!@", "aaaaaaaaaaaaaaaaaaaa1!"})
    public void 유저_비밀번호_검증_실패_테스트(final String password) {
        MemberSaveRequest request = new MemberSaveRequest(
                "power",
                password
        );

        Set<ConstraintViolation<MemberSaveRequest>> violations = validator.validate(request);
        Assertions.assertThat(violations.size()).isPositive();
    }
}
