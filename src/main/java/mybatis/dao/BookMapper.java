package mybatis.dao;

import java.util.List;
import mybatis.model.Book;
import org.mybatis.cdi.Mapper;

@Mapper
public interface BookMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.BOOK
     *
     * @mbg.generated Sun Apr 28 15:02:03 EEST 2024
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.BOOK
     *
     * @mbg.generated Sun Apr 28 15:02:03 EEST 2024
     */
    int insert(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.BOOK
     *
     * @mbg.generated Sun Apr 28 15:02:03 EEST 2024
     */
    Book selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.BOOK
     *
     * @mbg.generated Sun Apr 28 15:02:03 EEST 2024
     */
    List<Book> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.BOOK
     *
     * @mbg.generated Sun Apr 28 15:02:03 EEST 2024
     */
    int updateByPrimaryKey(Book record);
}