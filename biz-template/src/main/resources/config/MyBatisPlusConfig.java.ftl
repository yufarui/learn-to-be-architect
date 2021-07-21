package ${base}.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@MapperScan("${base}.mapper")
public class MyBatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Value("{spring.profiles.active}") String profile) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        if (profile.contains(DbType.MYSQL.getDb())) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        } else if (profile.contains(DbType.ORACLE.getDb())) {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.ORACLE));
        } else {
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        }

        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

    @Profile("oracle")
    @Bean
    public IdentifierGenerator oracleKeyGenerator() {
        return new DefaultIdentifierGenerator();
    }

    @Profile("mysql")
    @Bean
    public IdentifierGenerator mysqlKeyGenerator() {
        return new MySqlIdGenerator();
    }

}
