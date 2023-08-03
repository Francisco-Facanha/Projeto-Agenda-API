package Francisco.agendaapi.model.api.rest;

import Francisco.agendaapi.model.entity.Contato;
import Francisco.agendaapi.model.repository.ContatoRepository;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.aspectj.apache.bcel.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContatoController {
private final ContatoRepository repository;

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
 public Contato save (@RequestBody Contato contato ) {
 return repository.save(contato);
}

@DeleteMapping("{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void delete(@PathVariable Integer id){
repository.deleteById(id);
 }

 @GetMapping
 public Page<Contato> list(
         @RequestParam(value = "page", defaultValue = "0") Integer pagina,
         @RequestParam(value = "size", defaultValue = "10") Integer tamanhodapagina
 ){
    Sort sort = Sort.by(Sort.Direction.ASC,"nome");
  PageRequest pageRequest = PageRequest.of(pagina, tamanhodapagina);

return repository.findAll(pageRequest);
}

@PatchMapping("{id}/favorito")
public void favorite (@PathVariable Integer id){
 Optional <Contato> contato = repository.findById(id);
contato.ifPresent( c ->{
 boolean favorito = c.getFavorito() == Boolean.TRUE;
 c.setFavorito(!favorito);
 repository.save(c);
});
}
@PutMapping("{id}/foto")
 public byte[] addPhoto(@PathVariable Integer id,
                        @RequestParam("foto")Part arquivo) {
 Optional<Contato> contato = repository.findById(id);
return contato.map(c ->{
 try{
  InputStream is = arquivo.getInputStream();
  byte[] bytes = new byte[(int) arquivo.getSize()];
  IOUtils.readFully(is, bytes);
  c.setFoto(bytes);
  repository.save(c);
  is.close();
  return bytes;
 }catch (IOException e){
return null;
 }
}).orElse(null);
}
}
