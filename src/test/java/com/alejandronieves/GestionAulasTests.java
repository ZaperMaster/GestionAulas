package com.alejandronieves;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.Curso;
import com.alejandronieves.model.Edificio;
import com.alejandronieves.model.FamCurso;
import com.alejandronieves.model.Planta;
import com.alejandronieves.model.Reserva;
import com.alejandronieves.model.SubUsoAula;
import com.alejandronieves.model.TipoUsuario;
import com.alejandronieves.model.UsoAula;
import com.alejandronieves.model.Usuario;
import com.alejandronieves.repository.AulaRepository;
import com.alejandronieves.repository.CursoRepository;
import com.alejandronieves.repository.EdificioRepository;
import com.alejandronieves.repository.FamCursoRepository;
import com.alejandronieves.repository.PlantaRepository;
import com.alejandronieves.repository.ReservaRepository;
import com.alejandronieves.repository.SubUsoAulaRepository;
import com.alejandronieves.repository.TipoUsuarioRepository;
import com.alejandronieves.repository.UsoAulaRepository;
import com.alejandronieves.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GestionAulasTests {

    private static final String LUCIA = "lucia@test.local";
    private static final String JAVIER = "javier@test.local";
    private static final String CLAVE = "ClaveDePrueba1";
    private static final LocalDate DIA = LocalDate.now().plusDays(10);

    @Autowired private MockMvc mvc;
    @Autowired private UsuarioRepository usuarios;
    @Autowired private TipoUsuarioRepository tipos;
    @Autowired private AulaRepository aulas;
    @Autowired private EdificioRepository edificios;
    @Autowired private PlantaRepository plantas;
    @Autowired private CursoRepository cursos;
    @Autowired private FamCursoRepository familias;
    @Autowired private SubUsoAulaRepository subUsos;
    @Autowired private UsoAulaRepository usos;
    @Autowired private ReservaRepository reservas;
    @Autowired private PasswordEncoder encoder;

    private Planta planta;
    private Aula aula;
    private Aula otraAula;
    private Curso curso;

    @BeforeEach
    void preparar() {
        reservas.deleteAll();
        usuarios.findAll().stream()
                .filter(u -> !"admin@test.local".equals(u.getEmail()))
                .forEach(usuarios::delete);
        crearUsuario(LUCIA);
        crearUsuario(JAVIER);
        Edificio edificio = new Edificio();
        edificio.setDescripcion("Edificio A");
        planta = new Planta();
        planta.setDescripcion("Planta 1");
        planta.setEdificio(edificios.save(edificio));
        planta = plantas.save(planta);
        aula = aulas.save(nuevaAula("A-1"));
        otraAula = aulas.save(nuevaAula("A-2"));
        UsoAula uso = new UsoAula();
        uso.setDescripcion("Docencia");
        SubUsoAula subUso = new SubUsoAula();
        subUso.setDescripcion("Clase");
        subUso.setUsoAula(usos.save(uso));
        FamCurso familia = new FamCurso();
        familia.setDescripcion("Informática");
        familia.setSubUsoAula(subUsos.save(subUso));
        Curso c = new Curso();
        c.setDescripcion("DAM");
        c.setActivo(true);
        c.setFamCurso(familias.save(familia));
        curso = cursos.save(c);
    }

    private Aula nuevaAula(String nombre) {
        Aula a = new Aula();
        a.setNombreaula(nombre);
        a.setCapacidad(20);
        a.setPlanta(planta);
        return a;
    }

    private Usuario crearUsuario(String email) {
        TipoUsuario tipo = tipos.findById(3L).orElseThrow();
        Usuario u = new Usuario();
        u.setNombre("Prueba");
        u.setApellido("Test");
        u.setEmail(email);
        u.setPassword(encoder.encode(CLAVE));
        u.setTipoUsuario(tipo);
        return usuarios.save(u);
    }

    private static RequestPostProcessor como(String email) {
        return user(email).roles("USER");
    }

    private static RequestPostProcessor admin() {
        return user("admin@test.local").roles("ADMIN");
    }

    private ResultActions reservar(String email, Aula a, LocalDate desde, LocalDate hasta, String horaInicio, String horaFin) throws Exception {
        return mvc.perform(post("/reservas/procesar").with(como(email)).with(csrf())
                .param("aula.id", a.getId().toString())
                .param("cursoId", curso.getId().toString())
                .param("fechainicio", desde.toString())
                .param("fechafin", hasta.toString())
                .param("horainicio", horaInicio)
                .param("horafin", horaFin));
    }

    // ---------- Permisos ----------

    @Test
    void sinSesionSeRedirigeAlLogin() throws Exception {
        mvc.perform(get("/menuUsuario")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void unUsuarioNoEntraEnAdministracion() throws Exception {
        for (String ruta : new String[] {"/menu", "/usuarios", "/aulas", "/gestionarReservas"}) {
            mvc.perform(get(ruta).with(como(LUCIA))).andExpect(status().isForbidden());
        }
    }

    @Test
    void unAdministradorEntraEnAdministracion() throws Exception {
        for (String ruta : new String[] {"/menu", "/usuarios", "/aulas", "/gestionarReservas", "/menuUsuario"}) {
            mvc.perform(get(ruta).with(admin())).andExpect(status().isOk());
        }
    }

    @Test
    void laRaizLlevaAlMenuSegunElRol() throws Exception {
        mvc.perform(get("/").with(admin())).andExpect(redirectedUrl("/menu"));
        mvc.perform(get("/").with(como(LUCIA))).andExpect(redirectedUrl("/menuUsuario"));
    }

    @Test
    void borrarNoSePuedeHacerPorGet() throws Exception {
        mvc.perform(get("/aulas/borrar/" + aula.getId()).with(admin())).andExpect(status().isMethodNotAllowed());
        assertThat(aulas.findById(aula.getId())).isPresent();
    }

    // ---------- Registro y usuarios ----------

    @Test
    void elRegistroCreaUnUsuarioConLaClaveCifrada() throws Exception {
        mvc.perform(post("/registro").with(csrf())
                        .param("nombre", "Nueva").param("apellido", "Persona").param("dni", "12345678Z")
                        .param("email", "nueva@test.local").param("password", CLAVE).param("telefono", "600123456"))
                .andExpect(redirectedUrl("/login?registrado"));
        Usuario u = usuarios.findByEmail("nueva@test.local").orElseThrow();
        assertThat(u.getPassword()).isNotEqualTo(CLAVE);
        assertThat(encoder.matches(CLAVE, u.getPassword())).isTrue();
        assertThat(u.getTipoUsuario().getId()).isEqualTo(3L);
    }

    @Test
    void elRegistroRechazaUnEmailRepetido() throws Exception {
        mvc.perform(post("/registro").with(csrf())
                        .param("nombre", "Otra").param("apellido", "Persona").param("dni", "12345678Z")
                        .param("email", LUCIA).param("password", CLAVE).param("telefono", "600123456"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ya existe un usuario con este email")));
    }

    @Test
    void cambiarElRolNoEstropeaLaClave() throws Exception {
        Usuario u = usuarios.findByEmail(LUCIA).orElseThrow();
        mvc.perform(post("/usuarios/actualizar").with(admin()).with(csrf())
                        .param("id", u.getId().toString()).param("tipoUsuarioId", "1"))
                .andExpect(status().is3xxRedirection());
        Usuario cambiado = usuarios.findByEmail(LUCIA).orElseThrow();
        assertThat(cambiado.getTipoUsuario().getId()).isEqualTo(1L);
        assertThat(encoder.matches(CLAVE, cambiado.getPassword())).isTrue();
    }

    // ---------- Reservas ----------

    @Test
    void unaReservaValidaSeGuardaANombreDeQuienTieneLaSesion() throws Exception {
        mvc.perform(post("/reservas/procesar").with(como(LUCIA)).with(csrf())
                        .param("aula.id", aula.getId().toString()).param("cursoId", curso.getId().toString())
                        .param("usuario.id", usuarios.findByEmail(JAVIER).orElseThrow().getId().toString())
                        .param("fechainicio", DIA.toString()).param("fechafin", DIA.toString())
                        .param("horainicio", "09:00").param("horafin", "11:00"))
                .andExpect(redirectedUrl("/misreservas"));
        assertThat(reservas.findAll()).hasSize(1);
        assertThat(reservas.findAll().get(0).getUsuario().getEmail()).isEqualTo(LUCIA);
    }

    @Test
    void noSePuedeReservarUnAulaOcupada() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, aula, DIA, DIA, "10:00", "12:00")
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ya está reservada")));
        assertThat(reservas.count()).isEqualTo(1);
    }

    @Test
    void unaReservaDentroDeOtraTambienChoca() throws Exception {
        reservar(LUCIA, aula, DIA, DIA.plusDays(4), "09:00", "14:00").andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, aula, DIA.plusDays(2), DIA.plusDays(2), "10:00", "11:00")
                .andExpect(content().string(containsString("ya está reservada")));
        assertThat(reservas.count()).isEqualTo(1);
    }

    @Test
    void empezarJustoAlAcabarOtraSeReservaSinChoque() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, aula, DIA, DIA, "11:00", "13:00").andExpect(redirectedUrl("/misreservas"));
        assertThat(reservas.count()).isEqualTo(2);
    }

    @Test
    void otraAulaOOtroDiaNoChocan() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, otraAula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, aula, DIA.plusDays(1), DIA.plusDays(1), "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        assertThat(reservas.count()).isEqualTo(3);
    }

    @Test
    void unaReservaCanceladaLiberaElAula() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
        Long id = reservas.findAll().get(0).getId();
        mvc.perform(post("/reservas/borrar/" + id).with(como(LUCIA)).with(csrf()))
                .andExpect(redirectedUrl("/misreservas"));
        reservar(JAVIER, aula, DIA, DIA, "09:00", "11:00").andExpect(redirectedUrl("/misreservas"));
    }

    @Test
    void lasFechasYHorasIncoherentesSeRechazan() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "11:00", "09:00")
                .andExpect(content().string(containsString("hora de fin debe ser posterior")));
        reservar(LUCIA, aula, DIA, DIA, "09:00", "09:00")
                .andExpect(content().string(containsString("hora de fin debe ser posterior")));
        reservar(LUCIA, aula, DIA.plusDays(1), DIA, "09:00", "11:00")
                .andExpect(content().string(containsString("fecha final no puede ser anterior")));
        reservar(LUCIA, aula, LocalDate.now().minusDays(1), LocalDate.now(), "09:00", "11:00")
                .andExpect(content().string(containsString("anterior a hoy")));
        assertThat(reservas.count()).isZero();
    }

    @Test
    void misReservasMuestraLasDelUsuario() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00");
        mvc.perform(get("/misreservas").with(como(LUCIA))).andExpect(content().string(containsString("A-1")));
        mvc.perform(get("/misreservas").with(como(JAVIER))).andExpect(content().string(containsString("No tienes reservas")));
    }

    @Test
    void nadieCancelaLaReservaDeOtro() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00");
        Long id = reservas.findAll().get(0).getId();
        mvc.perform(post("/reservas/borrar/" + id).with(como(JAVIER)).with(csrf()))
                .andExpect(redirectedUrl("/misreservas"));
        assertThat(reservas.findById(id)).isPresent();
    }

    @Test
    void validarUnaReservaConservaSusDatos() throws Exception {
        reservar(LUCIA, aula, DIA, DIA, "09:00", "11:00");
        Long id = reservas.findAll().get(0).getId();
        mvc.perform(post("/gestionarReservas/editar").with(admin()).with(csrf())
                        .param("id", id.toString()).param("validar", "true"))
                .andExpect(redirectedUrl("/gestionarReservas"));
        Reserva r = reservas.findById(id).orElseThrow();
        assertThat(r.getValidar()).isTrue();
        assertThat(r.getActivo()).isTrue();
        assertThat(r.getHorainicio()).isEqualTo(LocalTime.of(9, 0));
    }
}
