package ModuloSeguridad.sprint.seguridad.Repositorios;
import ModuloSeguridad.sprint.seguridad.Modelos.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface RepositorioRol extends MongoRepository<Rol,String> {
}
