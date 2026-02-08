@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class LoanApplicationController {
    private final LoanApplicationRepository repo;
    private final LoanApplicationMapper mapper; // MapStruct

    @GetMapping
    public List<LoanApplicationDTO> list(@AuthenticationPrincipal UserDetails user) {
        // filter by dealer later
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @PostMapping
    public LoanApplicationDTO create(@RequestBody LoanApplicationDTO dto, @AuthenticationPrincipal UserDetails user) {
        LoanApplication entity = mapper.toEntity(dto);
        // set dealer from user later
        entity.setStatus("DRAFT");
        return mapper.toDTO(repo.save(entity));
    }

    // Add GET /:id, PUT /:id, DELETE later
}