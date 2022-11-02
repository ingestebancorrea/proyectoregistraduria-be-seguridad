package ModuloSeguridad.sprint.seguridad.Repositorios;


import ModuloSeguridad.sprint.seguridad.Modelos.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioUsuario extends MongoRepository<Usuario, String> {
}
