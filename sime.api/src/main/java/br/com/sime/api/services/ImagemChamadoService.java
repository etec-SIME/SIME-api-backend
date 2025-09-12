package br.com.sime.api.services;

import br.com.sime.api.entities.chamados.Chamado;
import br.com.sime.api.entities.chamados.ImagemChamado;
import br.com.sime.api.exceptions.NotFoundException;
import br.com.sime.api.repositories.ChamadoRepository;
import br.com.sime.api.repositories.ImagemChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImagemChamadoService {
    private final Path rootLocation  = Path.of("uploads");
    @Autowired
    private ImagemChamadoRepository imagemChamadoRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    public List<ImagemChamado> salvarImagens(Long chamadoId, MultipartFile[] files) throws IOException {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new NotFoundException("Chamado não encontrado"));

        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        List<ImagemChamado> imagens = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.getContentType().startsWith("image/")) {
                throw new RuntimeException("Apenas imagens são permitidas: " + file.getOriginalFilename());
            }

            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(fileName);
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            ImagemChamado imagemChamado = new ImagemChamado();
            imagemChamado.setNomeArquivo(file.getOriginalFilename());
            imagemChamado.setCaminho("/uploads/" + fileName);
            imagemChamado.setChamado(chamado);

            imagens.add(imagemChamado);
        }
        
        return imagemChamadoRepository.saveAll(imagens);
    }
}
