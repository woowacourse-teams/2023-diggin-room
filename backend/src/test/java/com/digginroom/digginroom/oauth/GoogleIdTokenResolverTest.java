package com.digginroom.digginroom.oauth;

import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.digginroom.digginroom.TestFixture.ID_TOKEN_RESOLVER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GoogleIdTokenResolverTest {

    private static final String EXPIRED_ID_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxMWUzOWUyNzkyOGFlOWYxZTlkMWUyMTY0NmRlOTJkMTkzNTFiNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjIwMzcyNTY1NjAtNm45b20wZm1ibTlkZ2FmN3RtdGFpamZhbmVkdjFybTEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjIwMzcyNTY1NjAtMWo1dTN2YnQ0NHRtMHJtaGI5cWo0bWg4bGFsbzNlMXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQxMDYwMDM2NjEyMjg5MjAzNzEiLCJlbWFpbCI6ImtpbWppbnVrMTk5OUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Iuq5gOynhOyasSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRkeUxlcXFheE9wS3l1QUZmNnpqbUJNX0xuRGJkNkJTMTNmSmtGczdQWHg9czk2LWMiLCJnaXZlbl9uYW1lIjoi7KeE7JqxIiwiZmFtaWx5X25hbWUiOiLquYAiLCJsb2NhbGUiOiJrbyIsImlhdCI6MTY5MTQ3NTcxMCwiZXhwIjoxNjkxNDc5MzEwfQ.O41Jfdb_Y0swfZ2r1oDG9NDO73dYYmPStzh8l90BEbwPoPQPeySBmRO9UnCnFaL_B6p18vKXfpgxOn6RDBwknHB6dRrRqElKFhEaCTp63TMMc2g-EijdsKDbhnUQWsTyuWqVflnNsCk0HQPU9_MevCuPIH-_gDdIHV6SQwVuZDWMjlzAdZXZCyyH09q22QMCsB1-zaPZHKHLShtGspRN5HUVvINhshB6Lip1OOe3cUFD-g41o3pFAOpDFbMMif1WVK5Lv6031SQL4GbhGN3Uum62-wgiU268uLwHYRFLNqkjR1guMNK4ieBmt46SkG1VOpfzcPE6I4p7x1lhUdN9Pg";
    private static final String SHORT_ID_TOKEN = "eyJbGciOiJSUzI1NiIsImtpZCI6IjkxMWUzOWUyNzkyOGFlOWYxZTlkMWUyMTY0NmRlOTJkMTkzNTFiNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjIwMzcyNTY1NjAtNm45b20wZm1ibTlkZ2FmN3RtdGFpamZhbmVkdjFybTEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjIwMzcyNTY1NjAtMWo1dTN2YnQ0NHRtMHJtaGI5cWo0bWg4bGFsbzNlMXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQxMDYwMDM2NjEyMjg5MjAzNzEiLCJlbWFpbCI6ImtpbWppbnVrMTk5OUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Iuq5gOynhOyasSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRkeUxlcXFheE9wS3l1QUZmNnpqbUJNX0xuRGJkNkJTMTNmSmtGczdQWHg9czk2LWMiLCJnaXZlbl9uYW1lIjoi7KeE7JqxIiwiZmFtaWx5X25hbWUiOiLquYAiLCJsb2NhbGUiOiJrbyIsImlhdCI6MTY5MTQ3NTcxMCwiZXhwIjoxNjkxNDc5MzEwfQ.O41Jfdb_Y0swfZ2r1oDG9NDO73dYYmPStzh8l90BEbwPoPQPeySBmRO9UnCnFaL_B6p18vKXfpgxOn6RDBwknHB6dRrRqElKFhEaCTp63TMMc2g-EijdsKDbhnUQWsTyuWqVflnNsCk0HQPU9_MevCuPIH-_gDdIHV6SQwVuZDWMjlzAdZXZCyyH09q22QMCsB1-zaPZHKHLShtGspRN5HUVvINhshB6Lip1OOe3cUFD-g41o3pFAOpDFbMMif1WVK5Lv6031SQL4GbhGN3Uum62-wgiU268uLwHYRFLNqkjR1guMNK4ieBmt46SkG1VOpfzcPE6I4p7x1lhUdN9P1";
    private static final String INVALID_ID_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxMWUzOWUyNzkyOGFlOWYxZTlkMWUyMTY0NmRlOTJkMTkzNTFiNDQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMjIwMzcyNTY1NjAtNm45b20wZm1ibTlkZ2FmN3RtdGFpamZhbmVkdjFybTEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxMjIwMzcyNTY1NjAtMWo1dTN2YnQ0NHRtMHJtaGI5cWo0bWg4bGFsbzNlMXIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQxMDYwMDM2NjEyMjg5MjAzNzEiLCJlbWFpbCI6ImtpbWppbnVrMTk5OUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Iuq5gOynhOyasSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRkeUxlcXFheE9wS3l1QUZmNnpqbUJNX0xuRGJkNkJTMTNmSmtGczdQWHg9czk2LWMiLCJnaXZlbl9uYW1lIjoi7KeE7JqxIiwiZmFtaWx5X25hbWUiOiLquYAiLCJsb2NhbGUiOiJrbyIsImlhdCI6MTY5MTQ3NTcxMCwiZXhwIjoxNjkxNDc5MzEwfQ.O41Jfdb_Y0swfZ2r1oDG9NDO73dYYmPStzh8l90BEbwPoPQPeySBmRO9UnCnFaL_B6p18vKXfpgxOn6RDBwknHB6dRrRqElKFhEaCTp63TMMc2g-EijdsKDbhnUQWsTyuWqVflnNsCk0HQPU9_MevCuPIH-_gDdIHV6SQwVuZDWMjlzAdZXZCyyH09q22QMCsB1-zaPZHKHLShtGspRN5HUVvINhshB6Lip1OOe3cUFD-g41o3pFAOpDFbMMif1WVK5Lv6031SQL4GbhGN3Uum62-wgiU268uLwHYRFLNqkjR1guMNK4ieBmt46SkG1VOpfzcPE6I4p7x1lhUdN9P1";
    
    @Test
    void 기간이_지난_ID_TOKEN을_사용하면_예외를_던진다() {
        assertThatThrownBy(() -> ID_TOKEN_RESOLVER.resolve(EXPIRED_ID_TOKEN))
                .isInstanceOf(InvalidIdTokenException.class);
    }

    @Test
    void 잘못된_길이의_ID_TOKEN은_예외를_던진다() {
        assertThatThrownBy(() -> ID_TOKEN_RESOLVER.resolve(SHORT_ID_TOKEN))
                .isInstanceOf(InvalidIdTokenException.class);
    }

    @Test
    void 잘못된_ID_TOKEN은_예외를_던진다() {
        assertThatThrownBy(() -> ID_TOKEN_RESOLVER.resolve(INVALID_ID_TOKEN))
                .isInstanceOf(InvalidIdTokenException.class);
    }
}
