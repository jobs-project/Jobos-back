package io.shifu.jobsearch.services;

import io.shifu.jobsearch.model.Job;
import io.shifu.jobsearch.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        List<Job> result = new ArrayList<>();
        // HARDCORE :D
        Job job1 = new Job();
        job1.setId(1l);
        job1.setSite("HH.ru");
        job1.setSiteId("25117514");
        job1.setDate(new Date());
        job1.setTitle("Java-разработчик");
        job1.setLocation("Novosibirsk");
        job1.setSalary(200000);
        job1.setCurrency("RUB");
        job1.setCompany("ТехноКад");
        job1.setDescription("<p><strong>Компания \"ТехноКад\" - один из крупнейших сервис-провайдеров и операторов электронного документооборота российского IT-рынка.</strong></p>");
        job1.setUrl("https://novosibirsk.hh.ru/vacancy/25117514");

        Job job2 = new Job();
        job2.setId(2l);
        job2.setSite("HH.ru");
        job2.setSiteId("26977533");
        job2.setDate(new Date());
        job2.setTitle("PHP-разработчик");
        job2.setLocation("Novosibirsk");
        job2.setSalary(null);
        job2.setCurrency(null);
        job2.setCompany("Albatross Internet Group");
        job2.setDescription("<p><strong>Albatross Internet Group</strong> – один из мировых лидеров по созданию online-сервисов экспертных консультаций.</p>");
        job2.setUrl("https://novosibirsk.hh.ru/vacancy/26977533");

        Job job3 = new Job();
        job3.setId(3l);
        job3.setSite("HH.ru");
        job3.setSiteId("19748126");
        job3.setDate(new Date());
        job3.setTitle("IOS разработчик");
        job3.setLocation("Novosibirsk");
        job3.setSalary(100000);
        job3.setCurrency("RUB");
        job3.setCompany("Сервис заказа такси Максим");
        job3.setDescription("<p><strong>Крупная компания, представленная, практически, в каждом российском городе, открывает отдел разработки в г. Новосибирск.</strong></p>");
        job3.setUrl("https://novosibirsk.hh.ru/vacancy/19748126");

        result.add(job1);
        result.add(job2);
        result.add(job3);

        job1.setId(4l);
        job2.setId(5l);
        job3.setId(6l);

        result.add(job1);
        result.add(job2);
        result.add(job3);

        job1.setId(7l);
        job2.setId(8l);
        job3.setId(9l);

        result.add(job1);
        result.add(job2);
        result.add(job3);

        return result;
    }
}
