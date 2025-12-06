package com.example.planitsquarebeproject.domain.country.service;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 사용
class CountryServiceTest {

    @Mock // 가짜 객체 생성
    private CountryRepository countryRepository;

    @InjectMocks // Mock 객체를 주입받는 실제 테스트 대상
    private CountryService countryService;

    @Test
    @DisplayName("모든 국가를 조회할 수 있다")
    void getAll() {
        // given: Repository가 반환할 데이터 설정
        List<Country> countries = List.of(
                new Country("KR", "South Korea"),
                new Country("US", "United States")
        );
        when(countryRepository.findAll()).thenReturn(countries);

        // when: 실제 메서드 호출
        List<CountryDto.Response> result = countryService.getAll();

        // then: 결과 검증
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCountryCode()).isEqualTo("KR");
        assertThat(result.get(1).getCountryCode()).isEqualTo("US");
        
        // Repository의 findAll이 1번 호출되었는지 확인
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("국가 코드로 특정 국가를 조회할 수 있다")
    void getByCode() {
        // given
        Country country = new Country("KR", "South Korea");
        when(countryRepository.findById("KR")).thenReturn(Optional.of(country));

        // when
        CountryDto.Response result = countryService.getByCode("KR");

        // then
        assertThat(result.getCountryCode()).isEqualTo("KR");
        assertThat(result.getName()).isEqualTo("South Korea");
    }

    @Test
    @DisplayName("국가가 없을 때 빈 리스트를 반환한다")
    void getAllWhenEmpty() {
        // given
        when(countryRepository.findAll()).thenReturn(List.of());

        // when
        List<CountryDto.Response> result = countryService.getAll();

        // then
        assertThat(result).isEmpty();
    }
}
