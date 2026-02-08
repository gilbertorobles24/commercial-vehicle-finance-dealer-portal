@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Dealer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    // later: role, etc.
}