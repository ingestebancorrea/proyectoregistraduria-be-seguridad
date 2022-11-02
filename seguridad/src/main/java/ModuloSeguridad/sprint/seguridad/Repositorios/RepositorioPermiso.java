package ModuloSeguridad.sprint.seguridad.Repositorios;

import ModuloSeguridad.sprint.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RepositorioPermiso extends MongoRepository<Permiso,String> {
}
