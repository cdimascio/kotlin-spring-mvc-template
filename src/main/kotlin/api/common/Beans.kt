package api.common

import api.users.UsersService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Beans {
    @Bean
    fun userService(): UsersService {
        return UsersService()
    }
}
