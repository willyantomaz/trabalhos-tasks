package com.example.minitrello.auth;

import com.example.minitrello.dto.AuthRequestDTO;
import com.example.minitrello.dto.AuthResponseDTO;
import com.example.minitrello.dto.RegistroDTO;
import com.example.minitrello.model.Usuario;
import com.example.minitrello.repository.UsuarioRepository;
import com.example.minitrello.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistroDTO registroDTO) {
        if (usuarioRepository.findByUsername(registroDTO.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Nome de usuário já está em uso!");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registroDTO.username());
        usuario.setPassword(passwordEncoder.encode(registroDTO.password()));

        usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        final UserDetails userDetails = usuarioRepository
                .findByUsername(authRequest.username())
                .orElseThrow(() -> new RuntimeException("Erro ao buscar detalhes do usuário após autenticação."));

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }
}