package com.fenix.backend.config;

import com.fenix.backend.domain.entity.Admin;
import com.fenix.backend.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void deveCriarAdminPadraoQuandoBancoEstaVazio() throws Exception {
        when(adminRepository.count()).thenReturn(0L);
        when(passwordEncoder.encode("admin123")).thenReturn("hash-bcrypt-simulado");

        dataInitializer.run(null);

        ArgumentCaptor<Admin> captor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(captor.capture());

        Admin adminSalvo = captor.getValue();
        assertThat(adminSalvo.getEmail()).isEqualTo("admin@fenix.com");
        assertThat(adminSalvo.getPasswordHash()).isEqualTo("hash-bcrypt-simulado");
        assertThat(adminSalvo.getName()).isEqualTo("Admin");
        assertThat(adminSalvo.isAcceptedTerms()).isTrue();
        assertThat(adminSalvo.getAcceptedTermsAt()).isNotNull();
    }

    @Test
    void naoDeveCriarAdminQuandoJaExisteUm() throws Exception {
        when(adminRepository.count()).thenReturn(1L);

        dataInitializer.run(null);

        verify(adminRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(anyString());
    }
}
