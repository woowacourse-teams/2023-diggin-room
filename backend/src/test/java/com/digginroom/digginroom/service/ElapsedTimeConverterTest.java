package com.digginroom.digginroom.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ElapsedTimeConverterTest {

    @ParameterizedTest
    @CsvSource(value = {"0:0초 전", "59:59초 전",}, delimiter = ':')
    void 초단위를_시간을_받으면_몇_초_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @ParameterizedTest
    @CsvSource(value = {"60:1분 전", "3599: 59분 전",}, delimiter = ':')
    void 분단위를_시간을_받으면_몇_분_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @ParameterizedTest
    @CsvSource(value = {"3600:1시간 전", "86399: 23시간 전",}, delimiter = ':')
    void 시단위를_시간을_받으면_몇_시간_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @ParameterizedTest
    @CsvSource(value = {"86400:1일 전", "604799: 6일 전",}, delimiter = ':')
    void 일단위를_시간을_받으면_몇_일_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @ParameterizedTest
    @CsvSource(value = {"604800:1주 전", "2629439: 4주 전",}, delimiter = ':')
    void 주단위를_시간을_받으면_몇_주_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @ParameterizedTest
    @CsvSource(value = {"2629440:1개월 전", "31535999: 11개월 전",}, delimiter = ':')
    void 월단위를_시간을_받으면_몇_개월_전인지_계산한다(final Long time, final String expect) {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(time);

        assertThat(convert).isEqualTo(expect);
    }

    @Test
    void 년단위를_시간을_받으면_몇_년_전인지_계산한다() {
        ElapsedTimeConverter elapsedTimeConverter = new ElapsedTimeConverter();

        String convert = elapsedTimeConverter.convert(31536000L);

        assertThat(convert).isEqualTo("1년 전");
    }
}
