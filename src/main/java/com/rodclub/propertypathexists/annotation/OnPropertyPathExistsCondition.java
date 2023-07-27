package com.rodclub.propertypathexists.annotation;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class OnPropertyPathExistsCondition extends SpringBootCondition {
	private static final String APPLICATION_PROPERTIES = "application";
	private static final String DOT = ".";

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		var configurableEnv = (ConfigurableEnvironment) context.getEnvironment();
		
		var propertyPath = (String) metadata
			.getAnnotationAttributes(ConditionalOnPropertyPathExist.class.getName())
			.get("value");
		
		var message = ConditionMessage
			.forCondition(ConditionalOnPropertyPathExist.class);
		
		return checkPropertyPathExists(propertyPath, configurableEnv)
			? ConditionOutcome.match(message.foundExactly(propertyPath))
			: ConditionOutcome.noMatch(message.didNotFind(propertyPath).atAll());
	}
	
	private boolean checkPropertyPathExists(
		final String propertyPath, 
		final ConfigurableEnvironment configurableEnv) {

		return configurableEnv.getPropertySources().stream()
			.filter(propSource -> propSource instanceof EnumerablePropertySource)
			.filter(propSource -> propSource.getName().contains(APPLICATION_PROPERTIES))
			.map(propSource -> (EnumerablePropertySource<?>) propSource)
			.map(enumerable -> enumerable.getPropertyNames())
			.flatMap(Arrays::stream)
			.anyMatch(propName -> propName.startsWith(propertyPath + DOT));
	}
}
