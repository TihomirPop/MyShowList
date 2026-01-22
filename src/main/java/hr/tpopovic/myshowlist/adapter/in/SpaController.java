package hr.tpopovic.myshowlist.adapter.in;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to handle SPA client-side routing.
 * <p>
 * Since SvelteKit uses client-side routing, we need to forward all non-API
 * requests to index.html so the client router can handle them.
 * This ensures that routes like /login, /register, /home work correctly
 * when the user refreshes the page or accesses them directly.
 */
@Controller
public class SpaController {

    @GetMapping(value = {
            "/",
            "/{path:[^.]*}"  // Match any path without a file extension
    })
    public String forward() {
        return "forward:/index.html";
    }
}
